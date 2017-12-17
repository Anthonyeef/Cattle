package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.Injection
import io.github.anthonyeef.cattle.constant.KEY_HOME_TIMELINE_LAST_UPDATE_TIME
import io.github.anthonyeef.cattle.constant.TIME_GOD_CREAT_LIGHT
import io.github.anthonyeef.cattle.contract.HomeFeedListContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.service.HomeTimelineService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 */
class HomeFeedListPresenter(): HomeFeedListContract.Presenter, AnkoLogger {

    companion object {
        val TTL = 10 * 60 * 1000
    }

    constructor(view: HomeFeedListContract.View): this() {
        homeFeedListView = view
        homeFeedListView.setPresenter(this)
    }

    lateinit var homeFeedListView: HomeFeedListContract.View

    lateinit var loadingCount: AtomicInteger

    var lastUpdateTime: Long = PrefUtils.getLong(KEY_HOME_TIMELINE_LAST_UPDATE_TIME, TIME_GOD_CREAT_LIGHT)

    var lastItemId: String = ""

    var isDataLoaded: Boolean = false

    var isDataOld: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            return (currentTime - lastUpdateTime) > TTL
        }
        set(v) = throw UnsupportedOperationException("Set method for isDataOld is not supported")

    var _disposable: CompositeDisposable = CompositeDisposable()


    override fun loadDataFromCache() {
        doAsync {
            info("load cache")
            val status: List<Status> = Injection.provideStatusDao().getStatus()
            uiThread {
                if (status.isNotEmpty()) {
                    homeFeedListView.updateTimeline(clearData = true, data = status)
                    isDataLoaded = true
                    lastItemId = status[status.size - 1].id
                }
            }
        }
    }


    override fun loadDataFromRemote(clearData: Boolean) {
        notifyLoadingStarted()
        val timelineService = ServiceGenerator.createDefaultService(HomeTimelineService::class.java)
        _disposable.add(timelineService.getHomeTimeline(lastId = if (clearData) "" else lastItemId)
                .subscribeOn(Schedulers.io())
                .doOnNext { statuses ->
                    val statusDao = Injection.provideStatusDao()
                    statusDao.insertStatuses(statuses)

                    statuses.forEach { status ->
                        status.repostStatus?.let {
                            it.ownerId = status.rawid
                            statusDao.insertRepostStatus(it)
                        }
                    }
                }
                .doFinally {
                    homeFeedListView.setLoadingProgressBar(false)
                    notifyLoadingFinished()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { statuses ->
                            homeFeedListView.updateTimeline(clearData = clearData, data = statuses)

                            isDataLoaded = true

                            // FIXME
                            if (isDataOld) {
                                homeFeedListView.scrollToTop()
                            }
                            lastUpdateTime = System.currentTimeMillis()
                            lastItemId = statuses[statuses.size - 1].id
                            PrefUtils.put(KEY_HOME_TIMELINE_LAST_UPDATE_TIME, lastUpdateTime)
                            info("load from remote")
                        },
                        { error ->
                            if (homeFeedListView.isActivated()) {
                                homeFeedListView.showError(error)
                            }
                        }
                ))
    }


    override fun subscribe() {
        loadingCount = AtomicInteger(0)

        if (!isDataLoaded) {
            loadDataFromCache()
        }
        if (isDataOld) {
            loadDataFromRemote(clearData = true)
        }
    }


    override fun unSubscribe() {
        _disposable.clear()
    }

    override fun isLoading(): Boolean {
        return loadingCount.get() > 0
    }

    private fun notifyLoadingStarted() {
        loadingCount.getAndIncrement()
    }

    private fun notifyLoadingFinished() {
        loadingCount.decrementAndGet()
    }
}
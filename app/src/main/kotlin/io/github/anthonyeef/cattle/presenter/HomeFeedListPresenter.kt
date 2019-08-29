package io.github.anthonyeef.cattle.presenter

import android.annotation.SuppressLint
import androidx.core.content.edit
import io.github.anthonyeef.cattle.Injection.statusDb
import io.github.anthonyeef.cattle.constant.KEY_HOME_TIMELINE_LAST_UPDATE_TIME
import io.github.anthonyeef.cattle.constant.TIME_GOD_CREAT_LIGHT
import io.github.anthonyeef.cattle.contract.HomeFeedListContract
import io.github.anthonyeef.cattle.service.HomeTimelineService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.utils.PrefUtils
import io.github.anthonyeef.cattle.utils.PrefUtils.defaultPref
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 */
class HomeFeedListPresenter(): HomeFeedListContract.Presenter {

    companion object {
        val TTL = 10 * 60 * 1000
    }

    constructor(view: HomeFeedListContract.View): this() {
        homeFeedListView = view
        homeFeedListView.setPresenter(this)
    }

    private lateinit var homeFeedListView: HomeFeedListContract.View
    private lateinit var loadingCount: AtomicInteger
    private var lastUpdateTime: Long = PrefUtils.getLong(KEY_HOME_TIMELINE_LAST_UPDATE_TIME, TIME_GOD_CREAT_LIGHT)
    private var lastItemId: String = ""
    private var isDataLoaded: Boolean = false
    private var _disposable: CompositeDisposable = CompositeDisposable()


    @SuppressLint("CheckResult")
    override fun loadDataFromCache() {
        Single.fromCallable { statusDb.getStatus() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    if (status.isNotEmpty()) {
                        homeFeedListView.updateTimeline(clearData = true, data = status, showAnimation = true)
                        isDataLoaded = true
                        lastItemId = status[status.size - 1].id
                    }
                }, {})
    }


    override fun loadDataFromRemote(clearData: Boolean) {
        notifyLoadingStarted()
        val timelineService = ServiceGenerator.createDefaultService(HomeTimelineService::class.java)
        _disposable.add(timelineService.getHomeTimeline(lastId = if (clearData) "" else lastItemId)
                .subscribeOn(Schedulers.io())
                .doOnNext { statuses ->
                    statusDb.insertStatuses(statuses)

                    statuses.forEach { status ->
                        status.repostStatus?.let {
                            it.ownerId = status.rawid
                            statusDb.insertRepostStatus(it)
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
                            lastUpdateTime = System.currentTimeMillis()
                            lastItemId = statuses[statuses.size - 1].id
                            defaultPref.edit {
                              putLong(KEY_HOME_TIMELINE_LAST_UPDATE_TIME, lastUpdateTime)
                            }
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
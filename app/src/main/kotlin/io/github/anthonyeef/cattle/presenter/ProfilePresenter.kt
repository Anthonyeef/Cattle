package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.ProfileContract
import io.github.anthonyeef.cattle.service.HomeTimelineService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.UserInfoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 */
class ProfilePresenter() : ProfileContract.Presenter {

    private var firstLoad: Boolean = true
    private val userInfoService = ServiceGenerator.createDefaultService(UserInfoService::class.java)
    private val userTimelineService = ServiceGenerator.createDefaultService(HomeTimelineService::class.java)
    private val _disposable: CompositeDisposable = CompositeDisposable()
    lateinit private var userId: String
    lateinit private var profileView: ProfileContract.View

    lateinit var loadingCount: AtomicInteger

    private var lastItemId: String = ""

    constructor(view: ProfileContract.View, id: String): this() {
        userId = id
        profileView = view
        profileView.setPresenter(this)
    }


    override fun loadProfile(forceUpdate: Boolean) {
        if (forceUpdate.not() && firstLoad.not()) {
            return
        }
        _disposable.add(userInfoService.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    loadStatuses(clearData = true)
                }
                .subscribe(
                        {
                            profileView.showProfile(it)
                            firstLoad = false
                        },
                        { error ->
                            // todo
                        }
                )
        )
    }


    override fun loadAlbumPreview() {
        _disposable.add(userTimelineService.getUserAlbumPreview(id = userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            profileView.showAlbumPreview(it)
                        },
                        // onError
                        {
                            // do nothing? or show error? (would be better)
                        }
                ))
    }


    override fun loadStatuses(clearData: Boolean) {
        notifyStatusLoadingStarted()
        _disposable.add(userTimelineService.getUserTimeline(id = userId, lastId = if (clearData) "" else lastItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    notifyStatusLoadingFinished()
                }
                .subscribe(
                        { statuses ->
                            profileView.showStatuses(statuses)
                            lastItemId = statuses.last().id
                        },
                        { error ->
                            // todo
                        }
                )
        )
    }


    override fun isStatusLoading(): Boolean {
        return loadingCount.get() > 0
    }


    override fun subscribe() {
        loadingCount = AtomicInteger(0)

//        loadAlbumPreview()
//        loadProfile(false)
    }


    override fun unSubscribe() {
        _disposable.clear()
    }


    private fun notifyStatusLoadingStarted() {
        loadingCount.getAndIncrement()
    }


    private fun notifyStatusLoadingFinished() {
        loadingCount.decrementAndGet()
    }
}
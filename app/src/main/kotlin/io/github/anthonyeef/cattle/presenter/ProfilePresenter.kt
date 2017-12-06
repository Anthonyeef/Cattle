package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.ProfileContract
import io.github.anthonyeef.cattle.service.HomeTimelineService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.UserInfoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
                    loadStatuses()
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

    override fun loadStatuses() {
        _disposable.add(userTimelineService.getUserTimeline(id = userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { statuses ->
                            profileView.showStatuses(statuses)
                        },
                        { error ->
                            // todo
                        }
                )
        )
    }

    override fun subscribe() {
        loadProfile(false)
    }

    override fun unSubscribe() {
        _disposable.clear()
    }
}
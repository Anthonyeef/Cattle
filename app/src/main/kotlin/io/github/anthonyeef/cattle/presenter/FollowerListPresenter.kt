package io.github.anthonyeef.cattle.presenter

import com.uber.autodispose.AutoDispose
import io.github.anthonyeef.cattle.contract.FollowersListContract
import io.github.anthonyeef.cattle.data.source.follower.FollowerRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 */
class FollowerListPresenter() : FollowersListContract.Presenter {

    lateinit private var followerListView: FollowersListContract.View
    lateinit private var followerListRepo: FollowerRepository
    lateinit private var userId: String

    constructor(view: FollowersListContract.View,
                repository: FollowerRepository,
                userIdData: String): this() {
        followerListView = view
        followerListRepo = repository
        userId = userIdData

        followerListView.setPresenter(this)
    }

    override fun loadFollowers() {
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose {

                }


        followerListRepo.getFollowers(userId)
                .`as`(AutoDispose)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }
}
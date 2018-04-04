package io.github.anthonyeef.cattle.viewmodel

import android.databinding.BaseObservable
import io.github.anthonyeef.cattle.data.source.follower.FollowerRepository
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable

/**
 * Created by wuyifen on 04/03/2018.
 */
class FollowerListViewModel : BaseObservable {

    private var followerRepository: FollowerRepository
    private var uid: String
    private var pageCount = 0


    constructor(repo: FollowerRepository, userId: String) {
        followerRepository = repo
        uid = userId
    }


    fun loadFollowerList(): Flowable<List<UserInfo>> {
        pageCount = 0
        return followerRepository.getFollowerList(uid, pageCount)
    }


    fun loadMoreFollowerList(): Flowable<List<UserInfo>> {
        pageCount++
        return followerRepository.getFollowerList(uid, pageCount)
    }
}
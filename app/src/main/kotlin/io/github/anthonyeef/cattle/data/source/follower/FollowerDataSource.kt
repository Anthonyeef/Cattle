package io.github.anthonyeef.cattle.data.source.follower

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable

/**
 * Created by wuyifen on 04/03/2018.
 */
interface FollowerDataSource {

    fun getFollowerList(userId: String, pageCount: Int): Flowable<List<UserInfo>>
}
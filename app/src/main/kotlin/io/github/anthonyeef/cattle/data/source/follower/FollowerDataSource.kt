package io.github.anthonyeef.cattle.data.source.follower

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable

/**
 *
 */
interface FollowerDataSource {

    fun getFollowers(userId: String): Flowable<List<UserInfo>>
}
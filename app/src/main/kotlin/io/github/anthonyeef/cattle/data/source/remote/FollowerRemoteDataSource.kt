package io.github.anthonyeef.cattle.data.source.remote

import io.github.anthonyeef.cattle.data.source.follower.FollowerDataSource
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.UserInfoService
import io.reactivex.Flowable

/**
 *
 */
class FollowerRemoteDataSource : FollowerDataSource {
    private val userInfoService = ServiceGenerator.createDefaultService(UserInfoService::class.java)

    companion object {
        private var INSTANCE: FollowerRemoteDataSource? = null

        fun getInstance(): FollowerRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = FollowerRemoteDataSource()
            }

            return INSTANCE!!
        }
    }

    override fun getFollowers(userId: String): Flowable<List<UserInfo>> {
        return userInfoService.getUserFollowers(userId)
    }
}
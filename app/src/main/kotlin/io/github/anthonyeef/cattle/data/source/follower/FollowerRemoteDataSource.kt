package io.github.anthonyeef.cattle.data.source.follower

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.UserInfoService
import io.reactivex.Flowable

/**
 * Created by wuyifen on 04/03/2018.
 */
class FollowerRemoteDataSource : FollowerDataSource {

    companion object {
        private var INSTANCE: FollowerDataSource? = null

        fun getInstance(): FollowerDataSource {
            if (INSTANCE == null) {
                INSTANCE = FollowerRemoteDataSource()
            }

            return INSTANCE!!
        }
    }


    private val userService = ServiceGenerator.createDefaultService(UserInfoService::class.java)


    override fun getFollowerList(userId: String, pageCount: Int): Flowable<List<UserInfo>> {
        return userService.getFollowerList(userId, pageCount)
    }
}
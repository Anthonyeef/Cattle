package io.github.anthonyeef.cattle.data.source.follower

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable

/**
 *
 */
class FollowerRepository() : FollowerDataSource {
    lateinit var followerRemoteDataSource: FollowerDataSource

    companion object {
        private var INSTANCE: FollowerRepository? = null

        fun getInstance(remoteDataSource: FollowerDataSource): FollowerRepository {
            if (INSTANCE == null) {
                INSTANCE = FollowerRepository(remoteDataSource)
            }
            return INSTANCE!!
        }
    }


    constructor(remoteDataSource: FollowerDataSource): this() {
        followerRemoteDataSource = remoteDataSource
    }


    override fun getFollowers(userId: String): Flowable<List<UserInfo>> {
        return followerRemoteDataSource.getFollowers(userId)
    }
}
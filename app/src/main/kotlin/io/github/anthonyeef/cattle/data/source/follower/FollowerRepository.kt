package io.github.anthonyeef.cattle.data.source.follower

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable

/**
 * Created by wuyifen on 04/03/2018.
 */
class FollowerRepository : FollowerDataSource {

    private var remoteFollowerDataSource: FollowerDataSource

    companion object {
        private var INSTANCE: FollowerRepository? = null

        fun getInstance(remoteDataSource: FollowerDataSource): FollowerRepository {
            if (INSTANCE == null) {
                INSTANCE = FollowerRepository(remoteDataSource)
            }

            return INSTANCE!!
        }
    }


    constructor(remoteDataSource: FollowerDataSource) {
        remoteFollowerDataSource = remoteDataSource
    }


    override fun getFollowerList(userId: String, pageCount: Int): Flowable<List<UserInfo>> {
        return remoteFollowerDataSource.getFollowerList(userId, pageCount)
    }
}
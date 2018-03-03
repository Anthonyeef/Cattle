package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface UserInfoService {

    @GET("users/show.json")
    fun getUserInfo(@Query("id") userId: String): Observable<UserInfo>

    @GET("users/followers.json")
    fun getUserFollowers(@Query("id") userId: String): Flowable<List<UserInfo>>
}
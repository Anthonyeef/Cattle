package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.entity.UserInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 */
interface AccountService {

    // 获取未授权的 requestToken
    // 接口地址：http://fanfou.com/oauth/access_token
    @GET
    fun oauthAccessToken(@Url url: String): Observable<String>

    @GET("account/verify_credentials.json")
    fun verifyCredential(): Observable<UserInfo>
}

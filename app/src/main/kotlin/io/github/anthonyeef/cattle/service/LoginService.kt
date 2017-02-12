package io.github.anthonyeef.cattle.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 */
interface LoginService {
    // 获取未授权的 requestToken
    // 接口地址：http://fanfou.com/oauth/request_token
    @GET
    fun oauthRequestToken(@Url url: String): Observable<String>
}
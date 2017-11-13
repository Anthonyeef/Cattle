package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.data.statusData.Status
import io.reactivex.Observable
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 *
 */
interface StatusService {

    @Multipart
    @POST("statuses/update.json")
    fun composeNewFanfou(
            @Part("status") status: String
    ): Observable<Status>
}
package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.entity.DirectMessage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface DirectMessageService {

    @GET("direct_messages/inbox.json")
    fun getDirectMessagesList(
            @Query("count") count: Int = 20
    ): Observable<List<DirectMessage>>
}
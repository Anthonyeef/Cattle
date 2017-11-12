package io.github.anthonyeef.cattle.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface MentionService {

    @GET("statuses/mentions.json")
    fun getMentionList(
            @Query("count") count: Int = 20,
            @Query("format") format: String = "html"
    ): Observable<List<Status>>
}
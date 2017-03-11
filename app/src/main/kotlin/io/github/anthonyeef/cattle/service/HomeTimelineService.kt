package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.entity.Status
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface HomeTimelineService {
    @GET("statuses/home_timeline.json")
    fun getHomeTimeline(
            @Query("count") count: Int = 10,
            @Query("format") format: String = "html",
            @Query("max_id") lastId: String = ""
    ): Observable<List<Status>>
}
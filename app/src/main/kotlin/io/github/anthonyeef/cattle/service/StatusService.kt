package io.github.anthonyeef.cattle.service

import io.github.anthonyeef.cattle.data.statusData.Status
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*

/**
 *
 */
interface StatusService {

  @Multipart
  @POST("statuses/update.json")
  fun composeNewFanfou(
      @Part("status") status: String
  ): Observable<Status>

  @GET("statuses/show.json")
  fun getFanfouById(@Query("id") id: String): Flowable<Status>

  @GET("statuses/context_timeline.json")
  fun getContextStatus(@Query("id") id: String,
                       @Query("format") format: String = "html"): Flowable<List<Status>>
}
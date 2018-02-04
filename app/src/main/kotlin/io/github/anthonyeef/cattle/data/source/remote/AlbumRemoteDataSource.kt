package io.github.anthonyeef.cattle.data.source.remote

import io.github.anthonyeef.cattle.data.source.AlbumDataSource
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.service.HomeTimelineService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.reactivex.Flowable

/**
 *
 */
class AlbumRemoteDataSource : AlbumDataSource {

    companion object {
        private var INSTANCE: AlbumRemoteDataSource? = null

        fun getInstance(): AlbumRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = AlbumRemoteDataSource()
            }

            return INSTANCE!!
        }
    }

    private val homeTimelineService = ServiceGenerator.createDefaultService(HomeTimelineService::class.java)

    override fun getAlbumPhotos(userId: String, lastId: String): Flowable<List<Status>> {
        return homeTimelineService.getUserGalleryTimeline(id = userId, lastId = lastId)
    }
}
package io.github.anthonyeef.cattle.data.source.album

import io.github.anthonyeef.cattle.data.statusData.Status
import io.reactivex.Flowable

/**
 *
 */
class AlbumRepository() : AlbumDataSource {

    lateinit private var albumRemoteDataSource: AlbumDataSource

    companion object {
        private var INSTANCE: AlbumRepository? = null

        fun getInstance(remoteDataSource: AlbumDataSource): AlbumRepository {
            if (INSTANCE == null) {
                INSTANCE = AlbumRepository(remoteDataSource)
            }
            return INSTANCE!!
        }
    }

    constructor(dataSource: AlbumDataSource): this() {
        albumRemoteDataSource = dataSource
    }

    override fun getAlbumPhotos(userId: String, lastId: String): Flowable<List<Status>> {
        return albumRemoteDataSource.getAlbumPhotos(userId, lastId)
    }
}
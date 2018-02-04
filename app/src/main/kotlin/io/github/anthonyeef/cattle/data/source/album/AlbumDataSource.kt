package io.github.anthonyeef.cattle.data.source

import io.github.anthonyeef.cattle.data.statusData.Status
import io.reactivex.Flowable

/**
 *
 */
interface AlbumDataSource {

    fun getAlbumPhotos(userId: String, lastId: String): Flowable<List<Status>>
}
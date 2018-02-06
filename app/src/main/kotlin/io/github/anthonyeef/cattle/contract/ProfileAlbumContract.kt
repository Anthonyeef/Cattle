package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 *
 */
interface ProfileAlbumContract {

    interface View : BaseView<Presenter> {
        fun showPhotoStatus(clearData: Boolean, data: List<Status>)
        fun showError(error: Throwable)
        fun showEmptyView()
        fun onError()
    }

    interface Presenter : BasePresenter {
        fun loadPhotos()
        fun isAlbumDataLoading(): Boolean
        fun hasMorePhotos(): Boolean
    }
}
package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.userData.UserInfo

/**
 * contract of user profile
 * see [io.github.anthonyeef.cattle.fragment.ProfileFragment]
 */
interface ProfileContract {

    interface View : BaseView<Presenter> {
        fun showProfile(userInfo: UserInfo)

        fun showAlbumPreview(photos: List<Status>)

        fun showStatuses(statuses: List<Status>)
    }

    interface Presenter : BasePresenter {

        fun loadProfile(forceUpdate: Boolean = false)

        fun loadAlbumPreview()

        fun loadStatuses(clearData: Boolean = false)

        fun isStatusLoading(): Boolean
    }
}
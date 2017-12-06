package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.userData.UserInfo

/**
 *
 */
interface ProfileContract {

    interface View : BaseView<Presenter> {
        fun showProfile(userInfo: UserInfo)

        fun showStatuses(statuses: List<Status>)
    }

    interface Presenter : BasePresenter {
        fun loadProfile(forceUpdate: Boolean = false)

        fun loadStatuses()
    }
}
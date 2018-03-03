package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.userData.UserInfo

/**
 *
 */
interface FollowersListContract {

    interface View : BaseView<Presenter> {
        fun showFollowers(data: List<UserInfo>)
    }

    interface Presenter : BasePresenter {
        fun loadFollowers()
    }
}
package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView

/**
 *
 */
interface HomeActivityContract {

    interface View : BaseView<Presenter> {
        fun showComposeActivity()
    }

    interface Presenter : BasePresenter {
        fun composeNewFanfou()
    }
}
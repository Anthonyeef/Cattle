package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView

/**
 *
 */
interface DebugContract {
    interface View : BaseView<Presenter> {
        fun goLogin()
    }

    interface Presenter : BasePresenter {
        fun isTokenGranted(): Boolean
    }
}
package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView

/**
 *
 */
interface DebugContract {
    interface View : BaseView<Presenter> {
        fun goLogin()
        fun goHome()
    }

    interface Presenter : BasePresenter
}
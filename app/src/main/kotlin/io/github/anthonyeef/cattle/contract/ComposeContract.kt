package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 * Contract for compose presenter and view
 */
interface ComposeContract {
    interface View : BaseView<Presenter> {
        fun sendFinish(success: Boolean)
        fun getStatus(): String
        fun getRepostStatusId(): String
    }

    interface Presenter : BasePresenter {
        fun sendFanfou()
        fun repostFanfou()
    }
}
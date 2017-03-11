package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.entity.DirectMessage

/**
 *
 */
interface DirectMessageContract {
    interface View : BaseView<Presenter> {
        fun showError(e: Throwable)
        fun setLoadingProgressBar(show: Boolean)
        fun updateList(clearData: Boolean = true, data: List<DirectMessage>)
        fun isActivated(): Boolean
    }

    interface Presenter : BasePresenter {
        fun loadDataFromRemote()
    }
}
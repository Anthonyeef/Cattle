package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 *
 */
interface MentionListContract {
    interface View : BaseView<Presenter> {
        fun showError(e: Throwable)
        fun setLoadingProgressBar(show: Boolean)
        fun updateList(clearData: Boolean = true, data: List<Status>)
        fun isActivated(): Boolean
    }

    interface Presenter : BasePresenter {
        fun loadDataFromRemote()
    }
}
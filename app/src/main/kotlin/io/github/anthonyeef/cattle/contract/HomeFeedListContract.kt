package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 *
 */
interface HomeFeedListContract {

    interface View : BaseView<Presenter> {
        fun showError(e: Throwable)
        fun setLoadingProgressBar(show: Boolean)
        fun setBottomLoadingProgressBar(show: Boolean)
        fun updateTimeline(clearData: Boolean, data: List<Status>)
        fun isActivated(): Boolean
        fun scrollToTop()
    }

    interface Presenter : BasePresenter {
        fun loadDataFromCache()
        fun loadDataFromRemote(clearData: Boolean)
        fun isLoading(): Boolean
    }
}
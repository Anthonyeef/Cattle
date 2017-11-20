package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 *
 */
interface StatusDetailContract {

    interface View : BaseView<Presenter> {
        fun showStatusDetail(item: Status)
    }

    interface Presenter : BasePresenter {
        fun loadStatusById(id: String)
    }
}
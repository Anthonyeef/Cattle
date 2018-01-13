package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.ConversationStatus
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 *
 */
interface StatusDetailContract {

    interface View : BaseView<Presenter> {
        // For status doesn't have children or parent
        fun showSingleStatus(item: Status)

        // For status with children
        fun showOriginalStatus(item: Status)

        // For status with parent
        fun showConversationStatus(item: ConversationStatus)
    }

    interface Presenter : BasePresenter {
        fun loadStatusById(id: String)
    }
}
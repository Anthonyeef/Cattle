package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.contract.StatusDetailContract
import io.github.anthonyeef.cattle.data.statusData.ConversationStatus
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.viewbinder.StatusConversationItemViewBinder
import io.github.anthonyeef.cattle.viewbinder.StatusConversationStartItemViewBinder
import io.github.anthonyeef.cattle.viewbinder.StatusItemDetailItemViewBinder

/**
 * Detail fragment of one status.
 *
 * Show more info than timeline list.
 */
class StatusDetailFragment : BaseListFragment(), StatusDetailContract.View {

    companion object {
        const val KEY_HAS_CONVERSATION = "key_has_conversation"
    }

    private var statusDetailPresenter: StatusDetailContract.Presenter? = null
    private val hasConversation: Boolean by lazy { arguments?.getBoolean(KEY_HAS_CONVERSATION, false)?: false }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(Status::class.java, StatusItemDetailItemViewBinder())
        adapter.register(ConversationStatus::class.java)
                .to(StatusConversationStartItemViewBinder(), StatusConversationItemViewBinder())
                .withLinker({
                    if (it.isStartOfConversation) {
                        0
                    } else {
                        1
                    }
                })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView =  super.onCreateView(inflater, container, savedInstanceState)
        disablePullToRefresh()

        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusDetailPresenter?.subscribe()
    }


    override fun customizeRecyclerView() {
        super.customizeRecyclerView()

        list?.layoutManager = LinearLayoutManager(app).apply { reverseLayout = hasConversation }
    }


    override fun setPresenter(presenter: StatusDetailContract.Presenter?) {
        statusDetailPresenter = presenter
    }


    override fun showSingleStatus(item: Status) {
        item.isSingle = true
        items.add(item)
        adapter.notifyDataSetChanged()
    }


    override fun showOriginalStatus(item: Status) {
        item.isSingle = false
        items.add(item)
        adapter.notifyDataSetChanged()
    }


    override fun showConversationStatus(item: ConversationStatus) {
        items.add(item)
        adapter.notifyDataSetChanged()
    }
}
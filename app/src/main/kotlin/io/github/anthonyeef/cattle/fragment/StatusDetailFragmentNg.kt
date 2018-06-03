package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.contract.StatusDetailContractNg
import io.github.anthonyeef.cattle.data.statusData.ConversationStatus
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.entity.ListHeaderViewEntity
import io.github.anthonyeef.cattle.presenter.StatusDetailPresenterNg
import io.github.anthonyeef.cattle.viewbinder.ContextStatusItemViewBinder
import io.github.anthonyeef.cattle.viewbinder.ListHeaderViewBinder
import io.github.anthonyeef.cattle.viewbinder.StatusItemDetailItemViewBinder
import me.drakeet.multitype.register
import org.jetbrains.anko.findOptional

class StatusDetailFragmentNg : BaseListFragment(), StatusDetailContractNg.View {

  companion object {
    const val KEY_STATUS_ID = "key_status_id"
  }


  private var statusDetailPresenter: StatusDetailContractNg.Presenter? = null
  private val statusId: String by lazy { arguments?.getString(KEY_STATUS_ID) ?: "" }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    adapter.register(StatusItemDetailItemViewBinder())
    adapter.register(ContextStatusItemViewBinder())
    adapter.register(ListHeaderViewBinder())

    StatusDetailPresenterNg(this, lifeScope)
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val contentView =  inflater.inflate(R.layout.fragment_toolbar_list, container, false)

    list = contentView?.findOptional(android.R.id.list)
    swipeRefreshLayout = contentView?.findOptional(R.id.swipe_refresh_layout)

    return contentView
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setToolbarTitle(R.string.page_title_detail)
    statusDetailPresenter?.loadOriginalStatusById(statusId)
  }


  override fun showOriginalStatus(item: Status) {
    items.add(item)
    adapter.notifyDataSetChanged()

    if (item.inReplyToStatusId.isNotEmpty()) {
      statusDetailPresenter?.loadContextStatus(item.id)
    }
  }


  override fun showStatusContext(data: List<Status>) {
    if (data.isNotEmpty()) {
      items.add(ListHeaderViewEntity(getString(R.string.text_status_context)))
      items.addAll(data.filterNot { it.id == statusId }.map { ConversationStatus(it) })
      adapter.notifyDataSetChanged()
    }
  }


  override fun setPresenter(presenter: StatusDetailContractNg.Presenter?) {
    statusDetailPresenter = presenter
  }
}
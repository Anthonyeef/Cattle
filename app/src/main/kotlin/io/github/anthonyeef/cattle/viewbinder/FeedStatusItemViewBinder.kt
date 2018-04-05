package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.ProfileActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_HAS_CONVERSATION
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_ID
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.ViewItemStatusBinding
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk25.listeners.onClick

class FeedStatusItemViewBinder : ItemViewBinder<Status, FeedStatusItemViewBinder.FeedStatusItemViewHolder>(), AnkoLogger {

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FeedStatusItemViewHolder {
    return FeedStatusItemViewHolder(ViewItemStatusBinding.inflate(inflater, parent, false))
  }


  override fun onBindViewHolder(holder: FeedStatusItemViewHolder, item: Status) {
    holder.bindStatus(item)
  }


  inner class FeedStatusItemViewHolder(binding: ViewItemStatusBinding): RecyclerView.ViewHolder(binding.root) {
    private val statusBinding = binding

    fun bindStatus(t: Status) {
      statusBinding.status = t
      statusBinding.root.onClick {
        app.startActivity(app.intentFor<StatusDetailActivity>(EXTRA_STATUS_ID to t.id, EXTRA_STATUS_HAS_CONVERSATION to t.inReplyToStatusId.isNotEmpty()).newTask())
      }
      statusBinding.avatar.onClick {
        app.startActivity(app.intentFor<ProfileActivity>(ProfileActivity.EXTRA_USER_ID to t.user?.id).newTask())
      }
      statusBinding.executePendingBindings()
    }
  }
}
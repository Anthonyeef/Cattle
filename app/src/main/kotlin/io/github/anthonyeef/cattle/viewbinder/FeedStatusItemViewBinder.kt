package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.StatusItemClickEventHandler
import io.github.anthonyeef.cattle.databinding.ViewItemStatusBinding
import me.drakeet.multitype.ItemViewBinder

class FeedStatusItemViewBinder : ItemViewBinder<Status, FeedStatusItemViewBinder.FeedStatusItemViewHolder>() {

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
      statusBinding.clickHandler = StatusItemClickEventHandler
      statusBinding.executePendingBindings()
    }
  }
}
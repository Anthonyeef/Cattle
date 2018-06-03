package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.data.statusData.ConversationStatus
import io.github.anthonyeef.cattle.databinding.StatusItemClickEventHandler
import io.github.anthonyeef.cattle.databinding.ViewItemStatusContextBinding
import me.drakeet.multitype.ItemViewBinder

class ContextStatusItemViewBinder : ItemViewBinder<ConversationStatus, ContextStatusItemViewBinder.ContextStatusItemViewHolder>() {

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ContextStatusItemViewHolder {
    return ContextStatusItemViewHolder(ViewItemStatusContextBinding.inflate(inflater, parent, false))
  }


  override fun onBindViewHolder(holder: ContextStatusItemViewHolder, item: ConversationStatus) {
    holder.bindData(item)
  }


  inner class ContextStatusItemViewHolder(binding: ViewItemStatusContextBinding): RecyclerView.ViewHolder(binding.root) {
    private val contextStatusBinding = binding

    fun bindData(t: ConversationStatus) {
      contextStatusBinding.clickHandler = StatusItemClickEventHandler
      contextStatusBinding.conversation = t
      contextStatusBinding.executePendingBindings()
    }
  }
}
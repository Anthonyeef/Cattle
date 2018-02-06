package io.github.anthonyeef.cattle.viewbinder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.databinding.ViewListHeaderBinding
import io.github.anthonyeef.cattle.entity.ListHeaderViewEntity
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class ListHeaderViewBinder : ItemViewBinder<ListHeaderViewEntity, ListHeaderViewBinder.ListHeaderViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ListHeaderViewHolder {
        return ListHeaderViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_list_header, parent, false))
    }


    override fun onBindViewHolder(holder: ListHeaderViewHolder, item: ListHeaderViewEntity) {
        holder.bindHeaderData(item)
    }

    inner class ListHeaderViewHolder(binding: ViewListHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        private val headerBinding = binding

        fun bindHeaderData(data: ListHeaderViewEntity) {
            headerBinding.titleEntity = data
            headerBinding.executePendingBindings()
        }
    }
}
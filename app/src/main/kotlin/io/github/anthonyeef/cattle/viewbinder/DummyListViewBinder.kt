package io.github.anthonyeef.cattle.viewbinder

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.databinding.ViewDummyListItemBinding
import io.github.anthonyeef.cattle.entity.DummyListViewEntity
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class DummyListViewBinder : ItemViewBinder<DummyListViewEntity, DummyListViewBinder.DummyListViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): DummyListViewHolder {
        return DummyListViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_dummy_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: DummyListViewHolder, item: DummyListViewEntity) {
        holder.bindDummyData(item)
    }

    inner class DummyListViewHolder(binding: ViewDummyListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val dummyBinding = binding

        fun bindDummyData(item: DummyListViewEntity) {
            dummyBinding.dummyEntity = item
            dummyBinding.executePendingBindings()
        }
    }
}
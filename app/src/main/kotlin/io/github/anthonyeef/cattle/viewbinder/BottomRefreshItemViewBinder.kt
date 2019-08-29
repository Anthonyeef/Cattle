package io.github.anthonyeef.cattle.viewbinder

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.databinding.ViewBottomRefreshBinding
import io.github.anthonyeef.cattle.entity.BottomRefreshEntity
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class BottomRefreshItemViewBinder : ItemViewBinder<BottomRefreshEntity, BottomRefreshItemViewBinder.BottomRefreshViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BottomRefreshViewHolder {
        return BottomRefreshViewHolder(ViewBottomRefreshBinding.inflate(inflater, parent, false))
    }


    override fun onBindViewHolder(holder: BottomRefreshViewHolder, t: BottomRefreshEntity) {
        holder.bindBottomRefresh(t)
    }


    inner class BottomRefreshViewHolder(binding: ViewBottomRefreshBinding): RecyclerView.ViewHolder(binding.root) {
        private val refreshBinding = binding

        fun bindBottomRefresh(data: BottomRefreshEntity) {
            refreshBinding.bottomRefresh = data
            refreshBinding.executePendingBindings()
        }
    }
}
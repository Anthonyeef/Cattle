package io.github.anthonyeef.cattle.viewbinder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.databinding.ViewEmptyHintBinding
import io.github.anthonyeef.cattle.entity.EmptyHintEntity
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class EmptyHintViewBinder : ItemViewBinder<EmptyHintEntity, EmptyHintViewBinder.EmptyHintViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): EmptyHintViewHolder {
        return EmptyHintViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_empty_hint, parent, false))
    }


    override fun onBindViewHolder(holder: EmptyHintViewHolder, item: EmptyHintEntity) {
        holder.bindData(item)
    }


    inner class EmptyHintViewHolder(binding: ViewEmptyHintBinding): RecyclerView.ViewHolder(binding.root) {
        private val hintBinding = binding

        fun bindData(data: EmptyHintEntity) {
            hintBinding.emptyHint = data
        }
    }
}
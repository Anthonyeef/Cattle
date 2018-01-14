package io.github.anthonyeef.cattle.viewbinder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.StatusItemClickEventHandler
import io.github.anthonyeef.cattle.databinding.ViewItemProfileStatusBinding
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class ProfileStatusViewBinder : ItemViewBinder<Status, ProfileStatusViewBinder.ProfileStatusViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfileStatusViewHolder {
        return ProfileStatusViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_item_profile_status, parent, false))
    }


    override fun onBindViewHolder(holder: ProfileStatusViewHolder, item: Status) {
        holder.bindStatusData(item)
    }


    inner class ProfileStatusViewHolder(binding: ViewItemProfileStatusBinding): RecyclerView.ViewHolder(binding.root) {
        private val statusBinding = binding

        fun bindStatusData(item: Status) {
            statusBinding.status = item
            statusBinding.clickHandler = StatusItemClickEventHandler
        }
    }
}
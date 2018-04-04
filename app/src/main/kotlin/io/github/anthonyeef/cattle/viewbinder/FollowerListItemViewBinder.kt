package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.ProfileActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.databinding.ViewItemFollowerBinding
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 * Created by wuyifen on 05/03/2018.
 */
class FollowerListItemViewBinder : ItemViewBinder<UserInfo, FollowerListItemViewBinder.FollowerListItemViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FollowerListItemViewHolder {
        return FollowerListItemViewHolder(ViewItemFollowerBinding.inflate(inflater, parent, false))
    }


    override fun onBindViewHolder(holder: FollowerListItemViewHolder, item: UserInfo) {
        holder.bindUserInfo(item)
    }


    inner class FollowerListItemViewHolder(binding: ViewItemFollowerBinding): RecyclerView.ViewHolder(binding.root) {
        private val followerItemBinding = binding

        fun bindUserInfo(data: UserInfo) {
            followerItemBinding.user = data
            followerItemBinding.root.onClick {
                app.startActivity(app.intentFor<ProfileActivity>(ProfileActivity.EXTRA_USER_ID to data.id).newTask())
            }
            followerItemBinding.executePendingBindings()
        }
    }
}
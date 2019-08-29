package io.github.anthonyeef.cattle.viewbinder

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.databinding.ViewItemFollowerBinding
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.extension.withArguments
import io.github.anthonyeef.cattle.fragment.ProfileFragment
import me.drakeet.multitype.ItemViewBinder

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
            followerItemBinding.root.setOnClickListener {
                ProfileFragment()
                        .withArguments(ProfileFragment.KEY_USER_ID to data.id)
                        .show()

            }
            followerItemBinding.executePendingBindings()
        }
    }
}
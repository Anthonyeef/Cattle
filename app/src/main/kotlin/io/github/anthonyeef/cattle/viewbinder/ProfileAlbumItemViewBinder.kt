package io.github.anthonyeef.cattle.viewbinder

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.StatusItemClickEventHandler
import io.github.anthonyeef.cattle.databinding.ViewItemProfileAlbumBinding
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class ProfileAlbumItemViewBinder : ItemViewBinder<Status, ProfileAlbumItemViewBinder.ProfileAlbumItemViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfileAlbumItemViewHolder {
        return ProfileAlbumItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_item_profile_album, parent, false))
    }


    override fun onBindViewHolder(holder: ProfileAlbumItemViewHolder, item: Status) {
        holder.bindPhotoData(item)
    }


    inner class ProfileAlbumItemViewHolder(itemViewBinding: ViewItemProfileAlbumBinding): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemViewBinding.root) {
        private val binding = itemViewBinding

        fun bindPhotoData(data: Status) {
            binding.clickHandler = StatusItemClickEventHandler
            binding.status = data
        }
    }
}
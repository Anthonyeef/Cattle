package io.github.anthonyeef.cattle.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.ViewItemProfileAlbumPreviewBinding

/**
 *
 */
class ProfilePreviewAlbumAdapter : RecyclerView.Adapter<ProfilePreviewAlbumAdapter.ProfileAlbumPreviewItemViewHolder>() {

    private var previewPhotos = mutableListOf<Status>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProfileAlbumPreviewItemViewHolder {
        return ProfileAlbumPreviewItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent?.context ?: app), R.layout.view_item_profile_album_preview, parent, false))
    }


    override fun onBindViewHolder(holder: ProfileAlbumPreviewItemViewHolder?, position: Int) {
        holder?.bindData(previewPhotos[position])
    }


    override fun getItemCount(): Int {
        return previewPhotos.size
    }


    inner class ProfileAlbumPreviewItemViewHolder(binding: ViewItemProfileAlbumPreviewBinding): RecyclerView.ViewHolder(binding.root) {
        private val binding = binding

        fun bindData(data: Status) {
            binding.status = data
            binding.executePendingBindings()
        }
    }


    fun setPreviewPhotos(data: List<Status>) {
        previewPhotos = data.toMutableList()
    }
}
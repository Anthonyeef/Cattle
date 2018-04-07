package io.github.anthonyeef.cattle.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.AlbumActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.ViewItemProfileAlbumPreviewBinding
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 *
 */
class ProfilePreviewAlbumAdapter : RecyclerView.Adapter<ProfilePreviewAlbumAdapter.ProfileAlbumPreviewItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAlbumPreviewItemViewHolder {
        return ProfileAlbumPreviewItemViewHolder(ViewItemProfileAlbumPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileAlbumPreviewItemViewHolder, position: Int) {
        holder.bindData(previewPhotos[position])
    }

    private var previewPhotos = mutableListOf<Status>()


    override fun getItemCount(): Int {
        return previewPhotos.size
    }


    inner class ProfileAlbumPreviewItemViewHolder(binding: ViewItemProfileAlbumPreviewBinding): RecyclerView.ViewHolder(binding.root) {
        private val binding = binding

        fun bindData(data: Status) {
            binding.status = data
            binding.image.onClick {
              app.startActivity(app.intentFor<AlbumActivity>(AlbumActivity.EXTRA_USER_ID to data.user?.id).newTask())
            }
            binding.executePendingBindings()
        }
    }


    fun setPreviewPhotos(data: List<Status>) {
        previewPhotos = data.toMutableList()
    }
}
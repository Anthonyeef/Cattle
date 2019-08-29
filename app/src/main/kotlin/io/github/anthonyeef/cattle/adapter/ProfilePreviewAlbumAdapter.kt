package io.github.anthonyeef.cattle.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.activity.AlbumActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.databinding.ViewItemProfileAlbumPreviewBinding

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
            binding.image.setOnClickListener {
                val intent = Intent(binding.root.context, AlbumActivity::class.java)
                intent.putExtra(AlbumActivity.EXTRA_USER_ID, data.user?.id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                app.startActivity(intent)
            }
            binding.executePendingBindings()
        }
    }


    fun setPreviewPhotos(data: List<Status>) {
        previewPhotos = data.toMutableList()
    }
}
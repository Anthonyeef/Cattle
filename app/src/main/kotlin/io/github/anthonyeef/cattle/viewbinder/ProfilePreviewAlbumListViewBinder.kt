package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.adapter.ProfilePreviewAlbumAdapter
import io.github.anthonyeef.cattle.entity.PreviewAlbumPhotos
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find

/**
 *
 */
class ProfilePreviewAlbumListViewBinder : ItemViewBinder<PreviewAlbumPhotos, ProfilePreviewAlbumListViewBinder.ProfilePreviewAlbumListViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfilePreviewAlbumListViewHolder {
        return ProfilePreviewAlbumListViewHolder(inflater.inflate(R.layout.view_profile_album_preview_list, parent, false))
    }


    override fun onBindViewHolder(holder: ProfilePreviewAlbumListViewHolder, item: PreviewAlbumPhotos) {
        holder.bindPreviewList(item)
    }


    inner class ProfilePreviewAlbumListViewHolder : RecyclerView.ViewHolder {
        private val previewListAdapter: ProfilePreviewAlbumAdapter by lazy { ProfilePreviewAlbumAdapter() }
        private val previewList: RecyclerView by lazy { itemView.find<RecyclerView>(android.R.id.list) }

        constructor(itemView: View): super(itemView) {
            val layoutManager = GridLayoutManager(itemView.context, 5)
            val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 1
                }
            }
            layoutManager.spanSizeLookup = spanSizeLookup

            previewList.layoutManager = layoutManager
            previewList.adapter = previewListAdapter
        }

        fun bindPreviewList(data: PreviewAlbumPhotos) {
            previewListAdapter.setPreviewPhotos(data.photos)
            previewListAdapter.notifyDataSetChanged()
        }
    }
}
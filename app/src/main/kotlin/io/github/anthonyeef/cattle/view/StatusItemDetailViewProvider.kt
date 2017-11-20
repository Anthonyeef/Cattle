package io.github.anthonyeef.cattle.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.utils.StatusParsingUtils
import io.github.anthonyeef.cattle.utils.TimeUtils
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find

/**
 *
 */
class StatusItemDetailViewProvider : ItemViewBinder<Status, StatusItemDetailViewProvider.StatusItemDetailViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): StatusItemDetailViewHolder {
        return StatusItemDetailViewHolder(inflater.inflate(R.layout.view_item_status_detail, parent, false))
    }


    override fun onBindViewHolder(holder: StatusItemDetailViewHolder, item: Status) {
        holder.bindStatusData(item)
    }


    inner class StatusItemDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val avatar: CircleImageView by lazy { itemView.find<CircleImageView>(R.id.avatar) }
        private val userName: TextView by lazy { itemView.find<TextView>(R.id.user_display_name) }
        private val userId: TextView by lazy { itemView.find<TextView>(R.id.user_id) }
        private val statusCreateTime: TextView by lazy { itemView.find<TextView>(R.id.status_create_time) }
        private val statusContent: TextView by lazy { itemView.find<TextView>(R.id.status_content) }
        private val statusPhoto: ImageView by lazy { itemView.find<ImageView>(R.id.status_photo) }

        fun bindStatusData(item: Status) {
            Glide.with(itemView.context)
                    .load(item.user?.profileImageUrlLarge)
                    .into(avatar)

            Glide.with(itemView.context)
                    .load(item.photo?.largeurl)
                    .into(statusPhoto)

            userName.text = item.user?.screenName
            userId.text = "@" + item.user?.id
            statusCreateTime.text = TimeUtils.getTime(item.createdAt) +
                    "\n\r" + TimeUtils.getDate(item.createdAt)
            if (item.text.isNotEmpty()) {
                StatusParsingUtils.setStatus(statusContent, item.text)
            }
        }
    }
}
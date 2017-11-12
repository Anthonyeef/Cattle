package io.github.anthonyeef.cattle.view

import android.annotation.SuppressLint
import android.support.v4.widget.Space
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.gone
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.utils.CatLogger
import io.github.anthonyeef.cattle.utils.StatusParsingUtils
import io.github.anthonyeef.cattle.utils.TimeUtils
import io.github.anthonyeef.cattle.utils.bindView
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class StatusItemViewProvider : ItemViewBinder<Status, StatusItemViewProvider.StatusFeedViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): StatusFeedViewHolder {
        val statusView = inflater.inflate(R.layout.view_item_status, parent, false)
        val viewHolder = StatusFeedViewHolder(statusView)
        viewHolder.bindView<RelativeLayout>(R.layout.view_item_status)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StatusFeedViewHolder, t: Status) {
        holder.bindData(t)
        holder.itemView.isClickable = true
    }

    inner class StatusFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CatLogger {
        val avatar: CircleImageView by bindView<CircleImageView>(R.id.avatar)
        val displayName: TextView by bindView<TextView>(R.id.user_display_name)
        val userName: TextView by bindView<TextView>(R.id.username)
        val createTime: TextView by bindView<TextView>(R.id.create_time)
        val content: TextView by bindView<TextView>(android.R.id.content)
        val previewImageTopMargin by bindView<Space>(R.id.margin_above_image)
        val previewImage: ImageView by bindView<ImageView>(R.id.status_image_preview)

        @SuppressLint("SetTextI18n")
        fun bindData(status: Status) {
            Glide.with(avatar.context)
                    .load(status.user?.profileImageUrlLarge)
                    .dontAnimate()
                    .into(avatar)
            displayName.text = status.user?.screenName
            userName.text = " @${status.user?.id}"
            createTime.text = TimeUtils.format(status.createdAt)
            if (status.text.isNotEmpty()) {
                StatusParsingUtils.setStatus(content, status.text)
            } else {
                if (status.photo != null) {
                    content.text = content.context.getString(R.string.text_photo_only_status)
                } else {
                    content.text = status.text
                }
            }
            if (status.photo?.imageurl?.isNotEmpty() ?: false) {
                previewImageTopMargin.show()
                previewImage.show()
                Glide.with(previewImage.context)
                        .load(status.photo?.largeurl)
                        .into(previewImage)
            } else {
                previewImageTopMargin.gone()
                previewImage.gone()
            }
        }
    }
}
package io.github.anthonyeef.cattle.view

import android.annotation.SuppressLint
import android.support.v4.widget.Space
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.ProfileActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_ID
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.gone
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.utils.StatusParsingUtils
import io.github.anthonyeef.cattle.utils.TimeUtils
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 *
 */
class StatusItemViewProvider : ItemViewBinder<Status, StatusItemViewProvider.StatusFeedViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): StatusFeedViewHolder {
        val statusView = inflater.inflate(R.layout.view_item_status, parent, false)
        return StatusFeedViewHolder(statusView)
    }

    override fun onBindViewHolder(holder: StatusFeedViewHolder, t: Status) {
        holder.bindData(t)
    }

    inner class StatusFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CatLogger {
        private val avatar: CircleImageView by lazy { itemView.find<CircleImageView>(R.id.avatar) }
        private val displayName: TextView by lazy { itemView.find<TextView>(R.id.user_display_name) }
        private val userName: TextView by lazy { itemView.find<TextView>(R.id.username) }
        private val createTime: TextView by lazy { itemView.find<TextView>(R.id.create_time) }
        private val content: TextView by lazy { itemView.find<TextView>(android.R.id.content) }
        private val previewImageTopMargin by lazy { itemView.find<Space>(R.id.margin_above_image) }
        private val previewImage: ImageView by lazy { itemView.find<ImageView>(R.id.status_image_preview) }

        @SuppressLint("SetTextI18n")
        fun bindData(status: Status) {
            Glide.with(avatar.context)
                    .load(status.user?.profileImageUrlLarge)
                    .into(avatar)
            avatar.onClick {
                status.user?.let {
                    app.startActivity(app.intentFor<ProfileActivity>(ProfileActivity.EXTRA_USER_ID to it.id).newTask())
                }
            }
            displayName.text = status.user?.screenName
            userName.text = " @${status.user?.id}"
            createTime.text = TimeUtils.prettyFormat(status.createdAt)
            if (status.text.isNotEmpty()) {
                StatusParsingUtils.setStatus(content, status.text)
            } else {
                if (status.photo != null) {
                    content.text = content.context.getString(R.string.text_photo_only_status)
                } else {
                    content.text = status.text
                }
            }
            content.onClick {
                app.startActivity(app.intentFor<StatusDetailActivity>(EXTRA_STATUS_ID to status.id).newTask())
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
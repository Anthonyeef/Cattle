package io.github.anthonyeef.cattle.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.StatusDetailActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_ID
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.goneIf
import io.github.anthonyeef.cattle.utils.StatusParsingUtils
import io.github.anthonyeef.cattle.utils.TimeUtils
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.onClick

/**
 *
 */
class ProfileStatusViewBinder : ItemViewBinder<Status, ProfileStatusViewBinder.ProfileStatusViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfileStatusViewHolder {
        return ProfileStatusViewHolder(inflater.inflate(R.layout.view_item_profile_status, parent, false))
    }


    override fun onBindViewHolder(holder: ProfileStatusViewHolder, item: Status) {
        holder.bindStatusData(item)
    }


    inner class ProfileStatusViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val location: TextView by lazy { itemView.find<TextView>(R.id.location) }
        private val createTime: TextView by lazy { itemView.find<TextView>(R.id.create_time) }
        private val content: TextView by lazy { itemView.find<TextView>(android.R.id.content) }
        private val statusPhoto: ImageView by lazy { itemView.find<ImageView>(R.id.status_photo) }
        private val statusSource: TextView by lazy { itemView.find<TextView>(R.id.source) }

        fun bindStatusData(item: Status) {
            location.text = item.location
            createTime.text = TimeUtils.prettyFormat(item.createdAt)
            if (item.text.isNotEmpty()) {
                StatusParsingUtils.setStatus(content, item.text)
            } else {
                if (item.photo != null) {
                    content.text = content.context.getString(R.string.text_photo_only_status)
                } else {
                    content.text = item.text
                }
            }

            statusPhoto.goneIf(item.photo == null)
            item.photo?.let {
                Glide.with(itemView.context)
                    .load(it.largeurl)
                    .into(statusPhoto)
            }

            StatusParsingUtils.setSource(statusSource, item.source)

            content.onClick {
                app.startActivity(app.intentFor<StatusDetailActivity>(EXTRA_STATUS_ID to item.id).newTask())
            }
        }
    }
}
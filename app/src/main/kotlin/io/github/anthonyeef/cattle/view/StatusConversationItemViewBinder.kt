package io.github.anthonyeef.cattle.view

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.statusData.ConversationStatus
import io.github.anthonyeef.cattle.extension.goneIf
import io.github.anthonyeef.cattle.utils.StatusParsingUtils
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find

/**
 *
 */
class StatusConversationItemViewBinder : ItemViewBinder<ConversationStatus, StatusConversationItemViewBinder.StatusConversationItemViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): StatusConversationItemViewHolder {
        return StatusConversationItemViewHolder(inflater.inflate(R.layout.view_item_status_conversation, parent, false))
    }


    override fun onBindViewHolder(holder: StatusConversationItemViewHolder, item: ConversationStatus) {
        holder.bindStatusData(item)
    }


    inner class StatusConversationItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val avatar: CircleImageView by lazy { itemView.find<CircleImageView>(R.id.avatar) }
        private val userName: TextView by lazy { itemView.find<TextView>(R.id.user_display_name) }
        private val statusContent: TextView by lazy { itemView.find<TextView>(R.id.status_content) }
        private val statusPhoto: ImageView by lazy { itemView.find<ImageView>(R.id.status_photo) }

        fun bindStatusData(item: ConversationStatus) {
            if (item.isStartOfConversation) {
                val layoutParams = itemView.layoutParams
                layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                itemView.layoutParams = layoutParams
            } else {
                val layoutParams = itemView.layoutParams
                layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                itemView.layoutParams = layoutParams
            }

            Glide.with(itemView.context)
                    .load(item.status.user?.profileImageUrlLarge)
                    .into(avatar)

            statusPhoto.goneIf(item.status.photo == null)
            item.status.photo?.let {
                Glide.with(itemView.context)
                    .load(it.largeurl)
                    .into(statusPhoto)
            }

            userName.text = item.status.user?.screenName
            if (item.status.text.isNotEmpty()) {
                StatusParsingUtils.setStatus(statusContent, item.status.text)
            }
        }
    }
}
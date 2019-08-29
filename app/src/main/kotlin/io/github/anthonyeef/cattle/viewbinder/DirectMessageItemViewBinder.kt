package io.github.anthonyeef.cattle.viewbinder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.entity.DirectMessage
import io.github.anthonyeef.cattle.utils.TimeUtils
import io.github.anthonyeef.cattle.utils.bindView
import me.drakeet.multitype.ItemViewBinder

/**
 *
 */
class DirectMessageItemViewBinder : ItemViewBinder<DirectMessage, DirectMessageItemViewBinder.DirectMessageViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): DirectMessageViewHolder {
        val directMessageView = inflater.inflate(R.layout.view_item_direct_message, parent, false)
        val viewHolder = DirectMessageViewHolder(directMessageView)
        viewHolder.bindView<RelativeLayout>(R.layout.view_item_direct_message)

        return viewHolder
    }

    override fun onBindViewHolder(holder: DirectMessageViewHolder, t: DirectMessage) {
        holder.bindData(t)
        holder.itemView.isClickable = true
    }

    inner class DirectMessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView by bindView(R.id.avatar)
        val displayName: TextView by bindView(R.id.user_display_name)
        val userName: TextView by bindView(R.id.username)
        val createTime: TextView by bindView(R.id.create_time)
        val content: TextView by bindView(android.R.id.content)

        @SuppressLint("SetTextI18n")
        fun bindData(data: DirectMessage) {
            Glide.with(avatar.context)
                    .load(data.sender?.profileImageUrlLarge)
                    .into(avatar)
            displayName.text = data.senderScreenName
            userName.text = " @${data.sender?.id}"
            createTime.text = TimeUtils.prettyFormat(data.createdAt)
            content.text = data.text
        }
    }
}
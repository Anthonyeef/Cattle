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
import io.github.anthonyeef.cattle.data.userData.UserInfo
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.find

/**
 *
 */
class ProfileItemViewBinder : ItemViewBinder<UserInfo, ProfileItemViewBinder.ProfileItemViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfileItemViewHolder {
        return ProfileItemViewHolder(inflater.inflate(R.layout.view_item_profile, parent, false))
    }


    override fun onBindViewHolder(holder: ProfileItemViewHolder, item: UserInfo) {
        holder.bindUserInfo(item)
    }

    inner class ProfileItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val profileBackground: ImageView by lazy { itemView.find<ImageView>(R.id.profile_bg) }
        private val profileAvatar: CircleImageView by lazy { itemView.find<CircleImageView>(R.id.avatar) }
        private val profileDisplayName: TextView by lazy { itemView.find<TextView>(R.id.user_display_name) }
        private val profileDescription: TextView by lazy { itemView.find<TextView>(R.id.description) }

        fun bindUserInfo(item: UserInfo) {
            Glide.with(itemView.context)
                    .load(item.profileBackgroundImageUrl)
                    .into(profileBackground)

            Glide.with(itemView.context)
                    .load(item.profileImageUrlLarge)
                    .into(profileAvatar)

            profileDisplayName.text = item.screenName
            profileDescription.text = item.description
        }
    }
}
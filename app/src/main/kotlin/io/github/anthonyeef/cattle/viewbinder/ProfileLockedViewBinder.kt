package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.databinding.ViewProfileLockedBinding
import me.drakeet.multitype.ItemViewBinder

class ProfileLockedViewBinder : ItemViewBinder<ProfileLockedViewBinder.ProfileLockedDummyEntity, ProfileLockedViewBinder.ProfileLockedViewHolder>() {

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProfileLockedViewHolder {
    return ProfileLockedViewHolder(ViewProfileLockedBinding.inflate(inflater, parent, false))
  }


  override fun onBindViewHolder(holder: ProfileLockedViewHolder, item: ProfileLockedDummyEntity) {
  }


  inner class ProfileLockedViewHolder(binding: ViewProfileLockedBinding): RecyclerView.ViewHolder(binding.root)


  class ProfileLockedDummyEntity
}
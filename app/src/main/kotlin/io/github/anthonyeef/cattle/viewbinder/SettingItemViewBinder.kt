package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anthonyeef.cattle.databinding.ViewSettingItemBinding
import io.github.anthonyeef.cattle.entity.SettingItemEntity
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.sdk25.listeners.onClick

class SettingItemViewBinder : ItemViewBinder<SettingItemEntity, SettingItemViewBinder.SettingItemViewHolder>() {

  interface SettingItemClickCallback {
    fun onSettingItemClicked(item: SettingItemEntity)
  }


  private var settingItemCallback: SettingItemClickCallback? = null


  fun registerClickedCallback(callback: SettingItemClickCallback): SettingItemViewBinder {
    settingItemCallback = callback

    return this
  }


  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SettingItemViewHolder {
    return SettingItemViewHolder(ViewSettingItemBinding.inflate(inflater, parent, false))
  }


  override fun onBindViewHolder(holder: SettingItemViewHolder, item: SettingItemEntity) {
    holder.bindSettingItem(item)
  }


  inner class SettingItemViewHolder(binding: ViewSettingItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val settingBinding = binding

    fun bindSettingItem(t: SettingItemEntity) {
      settingBinding.setting = t
      settingBinding.root.onClick {
        settingItemCallback?.onSettingItemClicked(t)
      }
      settingBinding.executePendingBindings()
    }
  }
}
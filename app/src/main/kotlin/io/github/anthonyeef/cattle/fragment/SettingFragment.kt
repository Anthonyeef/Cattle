package io.github.anthonyeef.cattle.fragment

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.HomeActivity
import io.github.anthonyeef.cattle.data.SettingCenter
import io.github.anthonyeef.cattle.entity.SettingItemEntity
import io.github.anthonyeef.cattle.utils.LocaleUtils
import io.github.anthonyeef.cattle.viewbinder.SettingItemViewBinder
import me.drakeet.multitype.register
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.selector
import java.util.*

class SettingFragment : BaseListFragment(), SettingItemViewBinder.SettingItemClickCallback {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)

    val root = inflater.inflate(R.layout.frag_setting, container, false)
    list = root?.findOptional(android.R.id.list)

    return root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setToolbarTitle(R.string.pref_title)
    setupRecyclerView()

    loadSettingItems()
  }


  private fun setupRecyclerView() {
    adapter.register(SettingItemViewBinder().registerClickedCallback(this))
  }


  private fun loadSettingItems() {
    items.add(SettingItemEntity(SettingCenter.language, getString(R.string.pref_item_language)))
    adapter.notifyDataSetChanged()
  }


  override fun onSettingItemClicked(item: SettingItemEntity) {
    when (item.id) {
      SettingCenter.language -> openLanguageSelector()
    }
  }


  private fun openLanguageSelector() {
    val languages = listOf(getString(R.string.text_language_default), getString(R.string.text_language_english), getString(R.string.text_language_chinese_simple))
    selector(getString(R.string.text_which_language), languages, { _, i ->
      var locale = Locale.getDefault().language
      when (i) {
        0 -> {
          locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
          } else {
            Resources.getSystem().configuration.locale.language
          }
        }
        1 -> {
          locale = Locale.ENGLISH.language
        }
        2 -> {
          locale = Locale.SIMPLIFIED_CHINESE.language
        }
      }

      context?.let {
        val newContext = LocaleUtils.setLocale(it, locale)
        newContext.startActivity(newContext.intentFor<HomeActivity>().clearTop())
      }
    })
  }
}
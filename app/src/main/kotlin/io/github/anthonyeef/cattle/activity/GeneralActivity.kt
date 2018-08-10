package io.github.anthonyeef.cattle.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.FragmentInstanceCache
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.matchParent

class GeneralActivity : BaseActivity() {

  companion object {
    val KEY_FRAG = "key_frag"
    val KEY_TINT_STATUS_BAR = "key_tint_status_bar"
  }


  private val shouldTintStatusBar: Boolean by lazy { intent.extras.getBoolean(KEY_TINT_STATUS_BAR) }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    coordinatorLayout {
      id = CONTAINER_ID
      lparams(width = matchParent, height = matchParent)
      fitsSystemWindows = true
    }


    val fragment = FragmentInstanceCache.get(intent.extras.getString(KEY_FRAG))

    fragment?.let {
      bindFragment(fragmentToShow = it)
    }
  }


  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    if (shouldTintStatusBar) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
      }
    }
  }
}
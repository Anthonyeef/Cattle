package io.github.anthonyeef.cattle.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.BaseFragment
import io.github.anthonyeef.cattle.fragment.FragmentInstanceCache
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.doFromSdk
import org.jetbrains.anko.matchParent

class GeneralActivity : BaseActivity() {

  companion object {
    val KEY_FRAG = "key_frag"
  }


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

    setupStatusBarColor()
    setNavigationBarColor()
  }


  private fun setupStatusBarColor() {
    doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
      window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    }
  }


  private fun setNavigationBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
  }
}
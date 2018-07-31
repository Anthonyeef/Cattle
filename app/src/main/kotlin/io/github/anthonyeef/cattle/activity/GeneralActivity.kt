package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.FragmentInstanceCache
import org.jetbrains.anko.design.coordinatorLayout
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
}
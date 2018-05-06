package io.github.anthonyeef.cattle.fragment

import android.support.v4.app.Fragment
import io.github.anthonyeef.cattle.extension.getKey

object FragmentInstanceCache {

  private val fragmentMap = HashMap<String, Fragment>()


  fun put(frag: Fragment) {
    fragmentMap.put(frag.getKey(), frag)
  }


  fun get(key: String): Fragment? {
    val frag: Fragment? = fragmentMap[key]
    frag?.let {
      fragmentMap.remove(key)
    }
    return frag
  }
}

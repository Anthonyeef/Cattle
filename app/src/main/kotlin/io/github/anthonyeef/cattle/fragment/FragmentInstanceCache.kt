package io.github.anthonyeef.cattle.fragment

import androidx.fragment.app.Fragment
import io.github.anthonyeef.cattle.extension.getKey

object FragmentInstanceCache {

  private val fragmentMap = HashMap<String, androidx.fragment.app.Fragment>()


  fun put(frag: androidx.fragment.app.Fragment) {
    fragmentMap.put(frag.getKey(), frag)
  }


  fun get(key: String): androidx.fragment.app.Fragment? {
    val frag: androidx.fragment.app.Fragment? = fragmentMap[key]
    frag?.let {
      fragmentMap.remove(key)
    }
    return frag
  }
}

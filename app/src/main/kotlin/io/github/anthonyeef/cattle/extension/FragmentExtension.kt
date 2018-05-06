package io.github.anthonyeef.cattle.extension

import android.app.Activity
import android.support.v4.app.Fragment
import io.github.anthonyeef.cattle.activity.GeneralActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.fragment.BaseFragment
import io.github.anthonyeef.cattle.fragment.FragmentInstanceCache
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor

/**
 *
 */
fun <T: Fragment> T.show(fragment: Fragment) {
    if (fragment.activity.isFragmentContainer()) {
        fragment.activity.showFragment(this)
    }
}

inline fun <reified T : Activity> Fragment.navigateTo() {
    startActivity(intentFor<T>())
}

fun Fragment.show() {
  FragmentInstanceCache.put(this)
  app.startActivity(app.intentFor<GeneralActivity>(GeneralActivity.KEY_FRAG to this.getKey()).newTask())
}

fun Fragment.getKey(): String {
  return String.format("%s@%s", this.javaClass.simpleName, this.hashCode())
}


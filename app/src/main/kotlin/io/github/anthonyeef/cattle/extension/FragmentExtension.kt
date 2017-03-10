package io.github.anthonyeef.cattle.extension

import android.app.Activity
import android.support.v4.app.Fragment
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


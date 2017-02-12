package io.github.anthonyeef.cattle.extension

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment

/**
 *
 */
fun <T: Fragment> T.show(fragment: Fragment) {
    if (fragment.activity.isFragmentContainer()) {
        fragment.activity.showFragment(this)
    }
}

inline fun <reified T : Activity> Fragment.navigateTo() {
    startActivity(Intent(this.activity, T::class.java))
}


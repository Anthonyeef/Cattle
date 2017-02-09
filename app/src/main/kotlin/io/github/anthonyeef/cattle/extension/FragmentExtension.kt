package io.github.anthonyeef.cattle.extension

import android.support.v4.app.Fragment

/**
 *
 */
fun <T: Fragment> T.show(fragment: Fragment) {
    if (fragment.activity.isFragmentContainer()) {
        fragment.activity.showFragment(this)
    }
}

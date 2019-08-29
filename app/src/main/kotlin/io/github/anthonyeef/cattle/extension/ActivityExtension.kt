package io.github.anthonyeef.cattle.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.view.ViewGroup
import io.github.anthonyeef.cattle.constant.CONTAINER_ID

/**
 * Extension methods for Activity
 */

/**
 * Method to show fragment inside activity.
 * add to back stack by default.
 *
 * @param containerId    container id to hold fragment
 * @param fragmentToShow fragment ready to show
 */
fun <T: androidx.fragment.app.FragmentActivity> T.showFragment(containerId: Int, fragmentToShow: androidx.fragment.app.Fragment) {
    supportFragmentManager.beginTransaction().replace(containerId, fragmentToShow)
            .addToBackStack(fragmentToShow.TAG)
            .commit()
}

/**
 * Method to show fragment inside activity.
 * Add to back stack by default.
 * Add to ViewGroup with id [CONTAINER_ID] by default
 *
 * @param fragmentToShow fragment ready to show
 */
fun <T: androidx.fragment.app.FragmentActivity?> T.showFragment(fragmentToShow: androidx.fragment.app.Fragment) {
    if (isFragmentContainer()) {
        this?.let {
            supportFragmentManager.beginTransaction().replace(CONTAINER_ID, fragmentToShow)
                    .addToBackStack(fragmentToShow.TAG)
                    .commit()
        }
    }
}

fun <T: androidx.fragment.app.FragmentActivity?> T.showFragment(fragmentToShow: androidx.fragment.app.Fragment, tag: String) {
    if (isFragmentContainer()) {
        this?.let {
            supportFragmentManager.beginTransaction()
                    .add(fragmentToShow, tag)
                    .commit()
        }
    }
}

/**
 * Method to check whether current activity have the container to hold fragment.
 * Default container with id [CONTAINER_ID]
 */
fun <T: androidx.fragment.app.FragmentActivity?> T.isFragmentContainer(): Boolean {
    return this?.findViewById<ViewGroup>(CONTAINER_ID) != null
}

/**
 * Method to bind fragment to activity.
 * This activity will be bind with it, without add to back stack.
 */
fun <T: androidx.fragment.app.FragmentActivity> T.bindFragment(fragmentToShow: androidx.fragment.app.Fragment) {
    if (isFragmentContainer()) {
        supportFragmentManager.beginTransaction()
                .add(CONTAINER_ID, fragmentToShow)
                .commit()
    }
}

package io.github.anthonyeef.cattle.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import io.github.anthonyeef.cattle.constant.CONTAINER_ID
import org.jetbrains.anko.findOptional

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
fun <T: FragmentActivity> T.showFragment(containerId: Int, fragmentToShow: Fragment) {
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
fun <T: FragmentActivity?> T.showFragment(fragmentToShow: Fragment) {
    if (isFragmentContainer()) {
        this?.let {
            supportFragmentManager.beginTransaction().replace(CONTAINER_ID, fragmentToShow)
                    .addToBackStack(fragmentToShow.TAG)
                    .commit()
        }
    }
}

fun <T: FragmentActivity?> T.showFragment(fragmentToShow: Fragment, tag: String) {
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
fun <T: FragmentActivity?> T.isFragmentContainer(): Boolean {
    return this?.findOptional<ViewGroup>(CONTAINER_ID) != null
}

/**
 * Method to bind fragment to activity.
 * This activity will be bind with it, without add to back stack.
 */
fun <T: FragmentActivity> T.bindFragment(fragmentToShow: Fragment) {
    if (isFragmentContainer()) {
        supportFragmentManager.beginTransaction()
                .add(CONTAINER_ID, fragmentToShow)
                .commit()
    }
}

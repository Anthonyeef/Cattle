package io.github.anthonyeef.cattle.extension

import android.app.Activity
import android.app.Fragment
import android.view.View
import io.github.anthonyeef.cattle.BasePresenter

/**
 * Some general kotlin extension,
 * use in everywhere: activity, fragment, view.
 */
val <T: Activity> T.TAG : String
    get() = javaClass.simpleName

val <T: Fragment> T.TAG : String
    get() = javaClass.simpleName

val <T: android.support.v4.app.Fragment> T.TAG : String
    get() = javaClass.simpleName

val <T: View> T.TAG : String
    get() = javaClass.simpleName

val <T: BasePresenter> T.TAG : String
    get() = javaClass.simpleName

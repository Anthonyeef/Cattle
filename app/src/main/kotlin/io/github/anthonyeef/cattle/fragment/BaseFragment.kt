package io.github.anthonyeef.cattle.fragment

import android.support.v4.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.utils.CatLogger

/**
 *
 */
abstract class BaseFragment : Fragment(), CatLogger {

    var lifeScope: AndroidLifecycleScopeProvider
        get() = AndroidLifecycleScopeProvider.from(this)
        set(value) {
            throw UnsupportedOperationException("Set method for AndroidLifecycleScopeProvider is not supported.")
        }
}

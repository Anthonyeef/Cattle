package io.github.anthonyeef.cattle.fragment

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.BaseActivity
import io.github.anthonyeef.cattle.utils.CatLogger
import org.jetbrains.anko.support.v4.findOptional

/**
 *
 */
abstract class BaseFragment : Fragment(), CatLogger {

  private val toolbar: Toolbar? by lazy { findOptional<Toolbar>(R.id.toolbar) }

  var lifeScope: AndroidLifecycleScopeProvider
    get() = AndroidLifecycleScopeProvider.from(this)
    set(value) {
      throw UnsupportedOperationException("Set method for AndroidLifecycleScopeProvider is not supported.")
    }


  fun setToolbarTitle(title: CharSequence) {
    (activity as? BaseActivity)?.setToolbarTitle(title)
  }


  fun setToolbarTitle(@StringRes title: Int) {
    setToolbarTitle(getString(title))
  }
}

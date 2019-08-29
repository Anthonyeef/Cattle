package io.github.anthonyeef.cattle.fragment

import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.BaseActivity

/**
 *
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

  private val toolbar: Toolbar? by lazy { view!!.findViewById<Toolbar>(R.id.toolbar) }

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

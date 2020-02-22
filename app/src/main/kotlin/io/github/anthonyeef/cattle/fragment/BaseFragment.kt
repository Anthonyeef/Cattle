package io.github.anthonyeef.cattle.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.BaseActivity

/**
 *
 */
abstract class BaseFragment : Fragment() {

  private val toolbar: Toolbar? by lazy { view!!.findViewById<Toolbar>(R.id.toolbar) }

  var lifeScope: AndroidLifecycleScopeProvider
    get() = AndroidLifecycleScopeProvider.from(this)
    set(value) {
      throw UnsupportedOperationException("Set method for AndroidLifecycleScopeProvider is not supported.")
    }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    activity?.let {
      it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      it.window.statusBarColor = Color.TRANSPARENT
    }
  }

  fun setToolbarTitle(title: CharSequence) {
    (activity as? BaseActivity)?.setToolbarTitle(title)
  }


  fun setToolbarTitle(@StringRes title: Int) {
    setToolbarTitle(getString(title))
  }
}

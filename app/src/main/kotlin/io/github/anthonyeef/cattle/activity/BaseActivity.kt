package io.github.anthonyeef.cattle.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.utils.LocaleUtils

open class BaseActivity : AppCompatActivity() {

  private val toolbar: Toolbar? by lazy { findViewById<Toolbar>(R.id.toolbar) }
  private var toolbarTitle: CharSequence = ""


  override fun attachBaseContext(newBase: Context?) {
    super.attachBaseContext(if (newBase != null) LocaleUtils.onAttach(newBase) else newBase)
  }


  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    setupToolbar()
  }


  private fun setupToolbar() {
    toolbar?.let {
      setSupportActionBar(it)

      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowTitleEnabled(false)
      supportActionBar?.elevation = 4.0f

      if (toolbarTitle.isNotEmpty()) {
        it.title = toolbarTitle
      }
    }
  }


  fun setToolbarTitle(title: CharSequence) {
    toolbarTitle = title
    toolbar?.title = toolbarTitle
  }
}

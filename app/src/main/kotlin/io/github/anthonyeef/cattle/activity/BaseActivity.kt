package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.anthonyeef.cattle.R
import org.jetbrains.anko.findOptional

open class BaseActivity : AppCompatActivity() {

    private val toolbar: Toolbar? by lazy { findOptional<Toolbar>(R.id.toolbar) }
    private var toolbarTitle: CharSequence = ""

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setupToolbar()
    }

    protected fun setToolbarTitle(title: CharSequence) {
        toolbarTitle = title
    }

    private fun setupToolbar() {
        toolbar?.let {
            setSupportActionBar(it)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.elevation = 4.0f

            it.title = toolbarTitle
        }
    }
}

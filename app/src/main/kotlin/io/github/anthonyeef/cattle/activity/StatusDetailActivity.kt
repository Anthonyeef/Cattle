package io.github.anthonyeef.cattle.activity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment.Companion.KEY_STATUS_ID
import io.github.anthonyeef.cattle.presenter.StatusDetailPresenter
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.support.v4.withArguments

/**
 *
 */
class StatusDetailActivity : BaseActivity() {

    private val toolbar: Toolbar? by lazy { findOptional<Toolbar>(R.id.toolbar) }

    companion object {
        const val EXTRA_STATUS_ID = "extra_status_id"
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_status_detail)
        setupToolbar()
        setupDetailFragment()

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        toolbar?.let {
            setSupportActionBar(it)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.elevation = 4.0f
            it.setTitle(R.string.page_title_detail)
        }
    }

    private fun setupDetailFragment() {
        val statusId = intent.getStringExtra(EXTRA_STATUS_ID)
        statusId?.let {
            val detailFragment = StatusDetailFragment().withArguments(KEY_STATUS_ID to it)
            bindFragment(detailFragment)
            StatusDetailPresenter(detailFragment, it)
        }
    }
}

package io.github.anthonyeef.cattle.activity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment.Companion.KEY_HAS_CONVERSATION
import io.github.anthonyeef.cattle.presenter.StatusDetailPresenter
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.support.v4.withArguments

/**
 * host activity for [StatusDetailFragment]
 */
class StatusDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_STATUS_ID = "extra_status_id"
        const val EXTRA_STATUS_HAS_CONVERSATION = "extra_status_has_conversation"
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_status_detail)
        setupDetailFragment()

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        setToolbarTitle(getString(R.string.page_title_detail))
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun setupDetailFragment() {
        val statusId = intent.getStringExtra(EXTRA_STATUS_ID)
        val hasConversation = intent.getBooleanExtra(EXTRA_STATUS_HAS_CONVERSATION, false)
        statusId?.let {
            val detailFragment = StatusDetailFragment().withArguments(KEY_HAS_CONVERSATION to hasConversation)
            bindFragment(detailFragment)
            StatusDetailPresenter(detailFragment, it)
        }
    }
}

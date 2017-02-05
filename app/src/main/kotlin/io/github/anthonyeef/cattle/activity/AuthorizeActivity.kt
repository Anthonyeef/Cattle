package io.github.anthonyeef.cattle.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.contract.AuthorizeContract
import io.github.anthonyeef.cattle.presenter.AuthorizePresenter
import org.jetbrains.anko.*

/**
 * Activity to manage authorize staff.
 */
class AuthorizeActivity : AppCompatActivity(), AuthorizeContract.View {
    lateinit var firstBtn: TextView
    lateinit var authorizeBtn: TextView
    lateinit var lastStepBtn: TextView
    lateinit var checkCredential: TextView
    lateinit var authorizePresenter: AuthorizeContract.Presenter

    override fun setPresenter(presenter: AuthorizeContract.Presenter) {
        authorizePresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Authorize"

        AuthorizePresenter(this@AuthorizeActivity)

        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            horizontalPadding = pageHorizontalPadding
            gravity = Gravity.CENTER

            firstBtn = textView("获取未授权的 request token") {
                lparams(width = wrapContent, height = wrapContent) {
                    topMargin = verticalPaddingNormal
                }
                verticalPadding = verticalPaddingNormal
                onClick {
                    authorizePresenter.fetchUnAuthorizedRequestToken()
                }
            }

            authorizeBtn = textView("Go authorize") {
                lparams(width = wrapContent, height = wrapContent) {
                    topMargin = verticalPaddingNormal
                }
                verticalPadding = verticalPaddingNormal
                onClick { goAuthorizeRequestToken() }
            }

            lastStepBtn = textView("Last step to get accessToken") {
                lparams(width = wrapContent, height = wrapContent) {
                    topMargin = verticalPaddingNormal
                }
                verticalPadding = verticalPaddingNormal

                onClick { authorizePresenter.fetchAccessToken() }
            }

            checkCredential = textView("Check credential") {
                lparams(width = wrapContent, height = wrapContent) {
                    topMargin = verticalPaddingNormal
                }
                verticalPadding = verticalPaddingNormal

                onClick { authorizePresenter.checkCredential() }
            }
        }
    }

    override fun goAuthorizeRequestToken() {
        val intent = Intent(Intent.ACTION_VIEW, authorizePresenter.getLoginAddress())
        startActivity(intent)
        this@AuthorizeActivity.finish()
    }

    override fun onError(e: Throwable?) {
        this@AuthorizeActivity.toast(e.toString())
    }

    override fun isActive(): Boolean {
        return true
    }
}
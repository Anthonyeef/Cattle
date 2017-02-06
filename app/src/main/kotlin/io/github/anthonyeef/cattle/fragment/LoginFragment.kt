package io.github.anthonyeef.cattle.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.contract.AuthorizeContract
import io.github.anthonyeef.cattle.presenter.AuthorizePresenter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast

/**
 *
 */
class LoginFragment : BaseFragment(), AuthorizeContract.View {
    lateinit var firstBtn: TextView
    lateinit var authorizeBtn: TextView
    lateinit var lastStepBtn: TextView
    lateinit var checkCredential: TextView
    lateinit var authorizePresenter: AuthorizeContract.Presenter

    override fun setPresenter(presenter: AuthorizeContract.Presenter) {
        authorizePresenter = presenter
    }

    override fun goAuthorizeRequestToken() {
        val intent = Intent(Intent.ACTION_VIEW, authorizePresenter.getLoginAddress())
        startActivity(intent)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onError(e: Throwable?) {
        toast(e.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthorizePresenter(this@LoginFragment)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                isClickable = true
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
        }.view
    }
}
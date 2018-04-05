package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.LoginEvent
import io.github.anthonyeef.cattle.constant.bus
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.LoginPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.toast

/**
 * User login and authorized.
 */
class LoginFragment : BaseFragment(), LoginContract.View {
    lateinit var loginBtn: Button
    lateinit var checkCredential: Button
    lateinit var mLoginPresenter: LoginContract.Presenter
    lateinit var _disposables: CompositeDisposable

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginPresenter(this@LoginFragment)
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        mLoginPresenter = presenter
    }

    override fun onStart() {
        super.onStart()
        _disposables = CompositeDisposable()
        _disposables.add(bus.asFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    if (event is LoginSuccessEvent) {
                        info("do fetch accessToken")
                        mLoginPresenter.fetchAccessToken()
                    }
                }
        )
    }

    override fun onResume() {
        super.onResume()
        mLoginPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mLoginPresenter.unSubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        _disposables.clear()
    }

    override fun showLoginSuccess() {
        Answers.getInstance()
            .logLogin(LoginEvent().putSuccess(true))
        toast("Login success!")
    }

    override fun showError(e: Throwable) {
        Answers.getInstance()
            .logLogin(LoginEvent().putSuccess(false))
        showException(this, e)
    }

    override fun goAuthorizeRequestToken() {
        mLoginPresenter.getLoginAddress()?.let {
            browse(it.toString(), false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                horizontalPadding = pageHorizontalPadding
                gravity = Gravity.CENTER

                loginBtn = button("Login") {
                    onClick {
                        mLoginPresenter.login()
                    }
                }

                checkCredential = button("Check credential") {
                    onClick { mLoginPresenter.checkCredential() }
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = verticalPaddingNormal
                }
            }
        }.view
    }
}
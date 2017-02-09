package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.anthonyeef.cattle.activity.BaseActivity
import io.github.anthonyeef.cattle.constant.pageHorizontalPadding
import io.github.anthonyeef.cattle.constant.verticalPaddingNormal
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.event.RxBus
import io.github.anthonyeef.cattle.presenter.LoginPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast

/**
 * User login and authorized.
 */
class LoginFragment : BaseFragment(), LoginContract.View {
    lateinit var loginBtn: Button
    lateinit var checkCredential: Button
    lateinit var mLoginPresenter: LoginContract.Presenter
    lateinit var _disposables: CompositeDisposable
    lateinit var _rxBus: RxBus

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _rxBus = (activity as BaseActivity).getRxBusSingleton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginPresenter(this@LoginFragment)
    }

    override fun onStart() {
        super.onStart()
        _disposables = CompositeDisposable()
        _disposables.add(_rxBus
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    if (event is LoginSuccessEvent) {
                        info("do fetch accessToken")
                        mLoginPresenter.fetchAccessToken()
                    }
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _disposables.clear()
    }

    override fun onError(e: Throwable?) {
        onUiThread {
            toast(e.toString())
        }
        error(e.toString())
    }

    override fun showLoginSuccess() {
        onUiThread {
            toast("Login success!")
        }
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        mLoginPresenter = presenter
    }

    override fun goAuthorizeRequestToken() {
        info("YEAHHH I am ready to jump to browser!")
        mLoginPresenter.getLoginAddress()?.let {
            browse(it.toString(), false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                    lparams(width = wrapContent, height = wrapContent) {
                        topMargin = verticalPaddingNormal
                    }

                    onClick { mLoginPresenter.checkCredential() }
                }
            }
        }.view
    }
}
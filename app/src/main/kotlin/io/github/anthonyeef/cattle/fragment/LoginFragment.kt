package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.LoginEvent
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.HomeActivity
import io.github.anthonyeef.cattle.constant.bus
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.LoginPresenter
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

/**
 * User login and authorized.
 */
class LoginFragment : BaseFragment(), LoginContract.View {
  private lateinit var mLoginPresenter: LoginContract.Presenter

  private val loginBtn: FloatingActionButton by lazy { find<FloatingActionButton>(R.id.login_btn) }
  private val name: TextView by lazy { find<TextView>(R.id.name_text) }
  private val cat: ImageView by lazy { find<ImageView>(R.id.cat) }
  private lateinit var _disposables: CompositeDisposable

  override fun isActive(): Boolean {
    return isAdded
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    LoginPresenter(this@LoginFragment)
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frag_login, container, false)
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    name.onClick {
      mLoginPresenter.checkCredential()
    }

    loginBtn.onClick {
      mLoginPresenter.login()
    }

    cat.onClick {
      mLoginPresenter.fetchAccessToken()
    }
  }

  override fun setPresenter(presenter: LoginContract.Presenter) {
    mLoginPresenter = presenter
  }

  override fun onStart() {
    super.onStart()

    _disposables = CompositeDisposable()
    _disposables.add(bus.asFlowable()
        .observeOn(mainThread)
        .subscribe { event ->
          if (event is LoginSuccessEvent) {
            info("do fetch accessToken")
            mLoginPresenter.fetchAccessToken()
          }
        })
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

    activity?.finish()

    startActivity(intentFor<HomeActivity>())
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
}
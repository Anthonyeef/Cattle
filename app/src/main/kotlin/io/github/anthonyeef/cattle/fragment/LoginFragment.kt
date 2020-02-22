package io.github.anthonyeef.cattle.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.LoginEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.activity.HomeActivity
import io.github.anthonyeef.cattle.constant.bus
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.event.LoginSuccessEvent
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.LoginPresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * User login and authorized.
 */
class LoginFragment : BaseFragment(), LoginContract.View {
  private lateinit var mLoginPresenter: LoginContract.Presenter

  private var loginBtn: FloatingActionButton? = null
  private var name: TextView? = null
  private var cat: ImageView? = null
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

    loginBtn = view.findViewById(R.id.login_btn)
    name = view.findViewById(R.id.name_text)
    cat = view.findViewById(R.id.cat)

    name?.setOnClickListener {
      mLoginPresenter.checkCredential()
    }

    loginBtn?.setOnClickListener {
      mLoginPresenter.login()
    }

    cat?.setOnClickListener {
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
      // fixme
//    toast("Login success!")

    activity?.finish()

    val intent = Intent(context, HomeActivity::class.java)
    startActivity(intent)
  }

  override fun showError(e: Throwable) {
    Answers.getInstance()
        .logLogin(LoginEvent().putSuccess(false))
    showException(this, e)
  }

  override fun goAuthorizeRequestToken() {
    mLoginPresenter.getLoginAddress()?.let { uri ->
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(uri.toString())
      startActivity(intent)
    }
  }
}
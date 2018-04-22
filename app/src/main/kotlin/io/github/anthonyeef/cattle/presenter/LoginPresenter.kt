package io.github.anthonyeef.cattle.presenter

import android.net.Uri
import androidx.content.edit
import io.github.anthonyeef.cattle.CattleSchedulers.io
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.Injection.userInfoDb
import io.github.anthonyeef.cattle.constant.*
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.data.AppDatabase
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.exception.DataNotFoundException
import io.github.anthonyeef.cattle.extension.getQueryParameter
import io.github.anthonyeef.cattle.service.AccountService
import io.github.anthonyeef.cattle.service.LoginService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.utils.PrefUtils.defaultPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Presenter for login.
 */
class LoginPresenter() : LoginContract.Presenter {
  lateinit var loginService: LoginService
  lateinit var loginView: LoginContract.View
  var _disposable: CompositeDisposable = CompositeDisposable()
  var accessToken: String = ""
  var accessTokenSecret: String = ""

  constructor(view: LoginContract.View) : this() {
    loginView = view
    loginView.setPresenter(this)
  }

  override fun subscribe() {
    if (loginView.isActive()) {
      // TODO
    }
  }

  override fun unSubscribe() {
    _disposable.clear()
  }

  override fun login() {
    loginService = ServiceGenerator.createService(LoginService::class.java,
        token = "", secret = "")
    _disposable.add(loginService.oauthRequestToken(FanfouRequestTokenUrl)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete { loginView.goAuthorizeRequestToken() }
        .subscribe(
            { result -> saveToken(result) },
            { error -> loginView.showError(error) }
        ))
  }

  override fun logout() {
    defaultPref.edit {
      putString(KEY_ACCESS_TOKEN, "")
      putString(KEY_ACCESS_TOKEN_SECRET, "")
      putString(KEY_CURRENT_USER_ID, "")
    }
    AppDatabase.getInstance(app).clearAllTables()
  }

  override fun fetchAccessToken() {
    _disposable.add(ServiceGenerator.createDefaultService(AccountService::class.java).oauthAccessToken(FanfouAccessTokenUrl)
        .subscribeOn(io)
        .observeOn(mainThread)
        .subscribe(
            {
              response -> saveToken(response)
            },
            {
              error -> loginView.showError(error)
            },
            {
              checkCredential()
            }
        ))
  }

  override fun checkCredential() {
    _disposable.add(ServiceGenerator.createDefaultService(AccountService::class.java).verifyCredential()
        .doOnNext {
          saveUserInfo(it)
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              loginView.showLoginSuccess()
            },
            {
              error -> loginView.showError(error)
            }
        ))
  }

  override fun getLoginAddress(): Uri? {
    if (accessToken.isNullOrBlank()) {
      loginView.showError(DataNotFoundException("Token not found"))
      return null
    } else {
      return Uri.parse(FanfouLoginBaseUrl)
          .buildUpon()
          .appendQueryParameter("oauth_token", accessToken)
          .appendQueryParameter("oauth_callback", CattleOauthCallbackUrl)
          .build()
    }
  }

  private fun saveToken(response: String) {
    accessToken = response.getQueryParameter("oauth_token")
    accessTokenSecret = response.getQueryParameter("oauth_token_secret")
    defaultPref.edit {
      putString(KEY_ACCESS_TOKEN, accessToken)
      putString(KEY_ACCESS_TOKEN_SECRET, accessTokenSecret)
    }
  }

  private fun saveUserInfo(user: UserInfo) {
    defaultPref.edit {
      putString(KEY_CURRENT_USER_ID, user.id)
    }
    userInfoDb.insertUserInfo(user)
  }
}
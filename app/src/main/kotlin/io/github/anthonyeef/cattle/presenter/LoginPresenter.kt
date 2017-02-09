package io.github.anthonyeef.cattle.presenter

import android.net.Uri
import io.github.anthonyeef.cattle.constant.*
import io.github.anthonyeef.cattle.contract.LoginContract
import io.github.anthonyeef.cattle.exception.ApiException
import io.github.anthonyeef.cattle.exception.DataNotFoundException
import io.github.anthonyeef.cattle.extension.getQueryParameter
import io.github.anthonyeef.cattle.service.AccountService
import io.github.anthonyeef.cattle.service.LoginService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.utils.CatLogger
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Presenter for login.
 */
class LoginPresenter() : LoginContract.Presenter, CatLogger {
    lateinit var loginService: LoginService
    lateinit var loginView: LoginContract.View
    var accessToken: String = ""
    var accessTokenSecret: String = ""

    constructor(view: LoginContract.View) : this() {
        loginView = view
        loginView.setPresenter(this)
    }

    override fun start() {
        if (loginView.isActive()) {
            // TODO
        }
    }

    override fun login() {
        doAsync({ loginView.onError(it) }) {
            loginService = ServiceGenerator.createService(LoginService::class.java,
                    token = "", secret = "")
            val call = loginService.oauthRequestToken(FanfouRequestTokenUrl)
            with(call.execute()) {
                if (isSuccessful) {
                    saveToken(body())
                    accessToken = body().getQueryParameter("oauth_token")
                    accessTokenSecret = body().getQueryParameter("oauth_token_secret")
                    uiThread { loginView.goAuthorizeRequestToken() }
                } else {
                    throw ApiException(code(), errorBody().string())
                }
            }
        }
    }

    override fun fetchAccessToken() {
        doAsync({ loginView.onError(it) }) {
            val call = ServiceGenerator.createDefaultService(AccountService::class.java).oauthAccessToken(FanfouAccessTokenUrl)
            with(call.execute()) {
                if (isSuccessful) {
                    saveToken(body())
                } else {
                    throw ApiException(code(), errorBody().string())
                }
            }
        }
    }

    override fun checkCredential() {
        doAsync({ loginView.onError(it) }) {
            val call = ServiceGenerator.createDefaultService(AccountService::class.java).verifyCredential()
            with(call.execute()) {
                if (isSuccessful) {
                    loginView.showLoginSuccess()
                } else {
                    throw ApiException(code(), errorBody().string())
                }
            }
        }
    }

    override fun getLoginAddress(): Uri? {
        if (accessToken.isNullOrBlank()) {
            loginView.onError(DataNotFoundException("Token not found"))
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
        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN, accessToken)
        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN_SECRET, accessTokenSecret)
    }
}
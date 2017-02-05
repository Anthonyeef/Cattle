package io.github.anthonyeef.cattle.presenter

import android.net.Uri
import io.github.anthonyeef.cattle.BuildConfig
import io.github.anthonyeef.cattle.constant.*
import io.github.anthonyeef.cattle.contract.AuthorizeContract
import io.github.anthonyeef.cattle.exception.ApiException
import io.github.anthonyeef.cattle.extension.TAG
import io.github.anthonyeef.cattle.extension.getQueryParameter
import io.github.anthonyeef.cattle.httpInterceptor.Oauth1SigningInterceptor
import io.github.anthonyeef.cattle.service.AccountService
import io.github.anthonyeef.cattle.service.LoginService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.model.UserInfo
import io.github.anthonyeef.cattle.utils.LogUtils
import io.github.anthonyeef.cattle.utils.SharedPreferenceUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 */
class AuthorizePresenter() : AuthorizeContract.Presenter {
    lateinit var loginService: LoginService
    lateinit var accountService: AccountService

    lateinit var accessToken: String
    lateinit var accessTokenSecret: String
    lateinit var authorizeView: AuthorizeContract.View

    constructor(view: AuthorizeContract.View) : this() {
        authorizeView = view
        authorizeView.setPresenter(this)
    }

    override fun start() {
        if (authorizeView.isActive()) {
            // TODO: check token valid
        }
    }

    override fun fetchUnAuthorizedRequestToken() {
        doAsync(exceptionHandler = { LogUtils.e(TAG, it.printStackTrace().toString()) }) {
            loginService = ServiceGenerator.createService(LoginService::class.java,
                    token = "", secret = "")
            val call = loginService.oauthRequestToken(FanfouRequestTokenUrl)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    if (response?.isSuccessful ?: false) {
                        val tempUri = Uri.EMPTY.buildUpon()
                                .encodedQuery(response?.body())
                                .build()

                        accessToken = tempUri.getQueryParameter("oauth_token")
                        accessTokenSecret = tempUri.getQueryParameter("oauth_token_secret")

                        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN, accessToken)
                        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN_SECRET, accessTokenSecret)

                    } else {
                        authorizeView.onError(ApiException(response!!.code(), response.message()))
                    }
                }

                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    // TODO: cause by network or other reason
                    authorizeView.onError(t)
                }
            })
        }
    }

    override fun fetchAccessToken() {
        doAsync(exceptionHandler = { LogUtils.e(TAG, it.printStackTrace().toString()) }) {
            val signingInterceptor = Oauth1SigningInterceptor.Builder()
                    .consumerKey(BuildConfig.API_KEY)
                    .consumerSecret(BuildConfig.API_SECRET)
                    .accessToken(SharedPreferenceUtils.getString(KEY_ACCESS_TOKEN))
                    .accessSecret(SharedPreferenceUtils.getString(KEY_ACCESS_TOKEN_SECRET))
                    .build()
            val tempClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor(signingInterceptor).build()
            accountService = ServiceGenerator.createService(AccountService::class.java, tempClient)
            val call = accountService.oauthAccessToken(FanfouAccessTokenUrl)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    if (response?.isSuccessful ?: false) {
                        val tempData = response?.body().toString()
                        accessToken = tempData.getQueryParameter("oauth_token")
                        accessTokenSecret = tempData.getQueryParameter("oauth_token_secret")

                        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN, accessToken)
                        SharedPreferenceUtils.putString(KEY_ACCESS_TOKEN_SECRET, accessTokenSecret)
                    } else {
                        authorizeView.onError(ApiException(response!!.code(), response.message()))
                    }
                }

                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    LogUtils.e(this@AuthorizePresenter.TAG, t?.cause)
                }
            })
        }
    }

    override fun checkCredential() {
        doAsync(exceptionHandler = { LogUtils.e(TAG, it.printStackTrace().toString()) }) {
            val call = ServiceGenerator.createDefaultService(AccountService::class.java).verifyCredential()
            call.enqueue(object : retrofit2.Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>?, response: Response<UserInfo>?) {
                    if (response?.isSuccessful ?: false) {
                        // TODO: save UserInfo
                    }
                }

                override fun onFailure(call: Call<UserInfo>?, t: Throwable?) {
                    LogUtils.e(this@AuthorizePresenter.TAG, t?.cause)
                }
            })
        }
    }

    override fun getLoginAddress(): Uri {
        return Uri.parse(FanfouLoginBaseUrl)
                            .buildUpon()
                            .appendQueryParameter("oauth_token", accessToken)
                            .appendQueryParameter("oauth_callback", CattleOauthCallbackUrl)
                            .build()
    }
}
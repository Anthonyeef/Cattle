package io.github.anthonyeef.cattle.repo

import io.github.anthonyeef.cattle.oauth.Api
import io.github.anthonyeef.cattle.oauth.ApiFactory
import io.github.anthonyeef.cattle.oauth.OAuthMethods
import org.oauthsimple.model.OAuthToken

/**
 *
 */
object AccountRepo : BaseRepo(), OAuthMethods {
    var _accessToken: OAuthToken? = null
    var _account: String? = null

    override fun getAccount(): String? {
        return _account
    }

    override fun setAccount(account: String?) {
        _account = account
    }

    override fun getOAuthRequestToken(): OAuthToken? {
        return _accessToken
    }

    override fun getOAuthAccessToken(username: String?, password: String?): OAuthToken {
        val api: Api = ApiFactory.getDefaultApi()
        return api.getOAuthAccessToken(username, password)
    }

    override fun setAccessToken(token: OAuthToken?) {
        _accessToken = token
    }
}
package io.github.anthonyeef.cattle.contract

import android.net.Uri
import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView

/**
 * This specifies the contract between AuthorizedActivity and the
 * AuthorizedPresenter.
 */
interface LoginContract {

    interface View : BaseView<Presenter> {
        fun goAuthorizeRequestToken()
        fun isActive(): Boolean
        fun showLoginSuccess()
        fun showError(e: Throwable)
    }

    interface Presenter : BasePresenter {
        fun login()
        fun logout()
        fun getLoginAddress(): Uri?
        fun fetchAccessToken()
        fun checkCredential()
    }
}
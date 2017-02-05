package io.github.anthonyeef.cattle.contract

import android.net.Uri
import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView

/**
 * This specifies the contract between AuthorizedActivity and the
 * AuthorizedPresenter.
 */
interface AuthorizeContract {

    interface View : BaseView<Presenter> {
        fun goAuthorizeRequestToken()
        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {
        fun fetchUnAuthorizedRequestToken()
        fun getLoginAddress(): Uri
        fun fetchAccessToken()
        fun checkCredential()
    }
}
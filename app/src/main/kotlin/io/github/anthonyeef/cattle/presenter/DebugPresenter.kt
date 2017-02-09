package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.DebugContract
import io.github.anthonyeef.cattle.service.AccountService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import org.jetbrains.anko.doAsyncResult

/**
 * presenter for [io.github.anthonyeef.cattle.fragment.DebugFragment]
 */
class DebugPresenter() : DebugContract.Presenter {
    lateinit var debugView: DebugContract.View

    constructor(view: DebugContract.View) : this() {
        debugView = view
        debugView.setPresenter(this)
    }

    override fun start() {
        // TODO: to do what?
    }

    override fun isTokenGranted(): Boolean {
        var result: Boolean
        try {
            result = doAsyncResult({ debugView.onError(it) }) {
                checkCredential()
            }.get()
        } catch (t: Throwable) {
            result = false
        }
        return result
    }

    private fun checkCredential(): Boolean {
        val accountService = ServiceGenerator.createDefaultService(AccountService::class.java)
        val call = accountService.verifyCredential()
        return call.execute().isSuccessful
    }
}
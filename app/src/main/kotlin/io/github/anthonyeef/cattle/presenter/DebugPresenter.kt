package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.DebugContract

/**
 * presenter for [io.github.anthonyeef.cattle.fragment.DebugFragment]
 */
class DebugPresenter() : DebugContract.Presenter {
    lateinit var debugView: DebugContract.View

    constructor(view: DebugContract.View) : this() {
        debugView = view
        debugView.setPresenter(this)
    }

    override fun subscribe() {
        // TODO: to do what?
    }

    override fun unSubscribe() {

    }
}
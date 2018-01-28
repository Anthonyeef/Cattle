package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.HomeActivityContract

/**
 *
 */
class HomeActivityPresenter() : HomeActivityContract.Presenter {

    private lateinit var homeView: HomeActivityContract.View

    constructor(view: HomeActivityContract.View): this() {
        homeView = view
        homeView.setPresenter(this)
    }


    override fun composeNewFanfou() {
        homeView.showComposeActivity()
    }


    override fun subscribe() {

    }


    override fun unSubscribe() {

    }
}
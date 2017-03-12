package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.ComposeContract
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.StatusService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class ComposePresenter() : ComposeContract.Presenter {
    lateinit var composeView: ComposeContract.View
    var _disposable: CompositeDisposable = CompositeDisposable()
    val composeService = ServiceGenerator.createDefaultService(StatusService::class.java)

    constructor(view: ComposeContract.View): this() {
        composeView = view
        composeView.setPresenter(this)
    }


    override fun subscribe() {

    }


    override fun unSubscribe() {
        _disposable.clear()
    }


    override fun sendFanfou() {
        _disposable.add(composeService.composeNewFanfou(status = composeView.getStatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { composeView.sendFinish(true) },
                        ::showException
                ))
    }
}
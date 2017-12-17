package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.DirectMessageContract
import io.github.anthonyeef.cattle.service.DirectMessageService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class DirectMessagePresenter() : DirectMessageContract.Presenter {
    lateinit var directListView: DirectMessageContract.View
    var isDataLoaded: Boolean = false
    var _disposable: CompositeDisposable = CompositeDisposable()

    constructor(view: DirectMessageContract.View): this() {
        directListView = view
        directListView.setPresenter(this)
    }

    override fun loadDataFromRemote() {
        val directMessageService = ServiceGenerator.createDefaultService(DirectMessageService::class.java)
        _disposable.add(directMessageService.getDirectMessagesList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { directListView.setLoadingProgressBar(true) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally { directListView.setLoadingProgressBar(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { status ->
                            directListView.updateList(data = status)
                            isDataLoaded = true
                        },
                        { error ->
                            if (directListView.isActivated()) {
                                directListView.showError(error)
                            }
                        }
                ))
    }

    override fun subscribe() {
        if (!isDataLoaded && directListView.isActivated()) {
            loadDataFromRemote()
        }
    }

    override fun unSubscribe() {
        _disposable.clear()
    }
}
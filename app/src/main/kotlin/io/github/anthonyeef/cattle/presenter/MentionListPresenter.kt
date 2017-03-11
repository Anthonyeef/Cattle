package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.MentionListContract
import io.github.anthonyeef.cattle.service.MentionService
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class MentionListPresenter() : MentionListContract.Presenter {
    lateinit var mentionList: MentionListContract.View
    var isDataLoaded: Boolean = false
    var _disposable: CompositeDisposable = CompositeDisposable()

    constructor(view: MentionListContract.View): this() {
        mentionList = view
        mentionList.setPresenter(this)
    }

    override fun loadDataFromRemote() {
        val mentionService = ServiceGenerator.createDefaultService(MentionService::class.java)
        _disposable.add(mentionService.getMentionList(count = 40)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mentionList.setLoadingProgressBar(true) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally { mentionList.setLoadingProgressBar(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { status ->
                            mentionList.updateList(data = status)
                            isDataLoaded = true
                        },
                        { error ->
                            if (mentionList.isActivated()) {
                                mentionList.showError(error)
                            }
                        }
                ))
    }

    override fun subscribe() {
        if (!isDataLoaded && mentionList.isActivated()) {
            loadDataFromRemote()
        }
    }

    override fun unSubscribe() {
        _disposable.clear()
    }
}
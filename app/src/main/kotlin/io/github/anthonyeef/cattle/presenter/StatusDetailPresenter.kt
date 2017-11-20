package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.Injection
import io.github.anthonyeef.cattle.contract.StatusDetailContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.StatusService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class StatusDetailPresenter() : StatusDetailContract.Presenter {

    lateinit private var statusDetailView: StatusDetailContract.View
    lateinit private var statusId: String
    private val _disposable: CompositeDisposable = CompositeDisposable()
    private val statusService = ServiceGenerator.createDefaultService(StatusService::class.java)


    constructor(view: StatusDetailContract.View, id: String): this() {
        statusId = id
        statusDetailView = view
        statusDetailView.setPresenter(this)
    }


    override fun loadStatusById(id: String) {
        _disposable.add(getStatusById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { status ->
                            statusDetailView.showStatusDetail(status)
                        },
                        { error ->
                            // todo
                        }
                ))
    }


    private fun getStatusById(id: String): Flowable<Status> {
        return Flowable.concat(Injection.provideStatusDao().getStatusById(id), statusService.getFanfouById(id))
    }


    override fun subscribe() {
        loadStatusById(statusId)
    }


    override fun unSubscribe() {
        _disposable.clear()
    }
}
package io.github.anthonyeef.cattle.presenter

import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.CattleSchedulers.io
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.contract.StatusDetailContractNg
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.StatusService

class StatusDetailPresenterNg() : StatusDetailContractNg.Presenter {

  private lateinit var statusDetailView: StatusDetailContractNg.View
  private lateinit var lifecycleScopeProvider: AndroidLifecycleScopeProvider
  private val statusService = ServiceGenerator.createDefaultService(StatusService::class.java)


  constructor(view: StatusDetailContractNg.View, lifeProvider: AndroidLifecycleScopeProvider): this() {
    statusDetailView = view
    statusDetailView.setPresenter(this)
    lifecycleScopeProvider = lifeProvider
  }


  override fun loadOriginalStatusById(id: String) {
    if (id.isNotEmpty()) {
      statusService.getFanfouById(id)
        .subscribeOn(io)
        .observeOn(mainThread)
        .`as`(autoDisposable(lifecycleScopeProvider))
        .subscribe(
            {
              statusDetailView.showOriginalStatus(it)
            },
            {
              // todo
            }
        )
    }
  }


  override fun loadContextStatus(id: String) {
    if (id.isNotEmpty()) {
      statusService.getContextStatus(id)
          .subscribeOn(io)
          .observeOn(mainThread)
          .`as`(autoDisposable(lifecycleScopeProvider))
          .subscribe(
              {
                statusDetailView.showStatusContext(it)
              },
              {
                // todo
              }
          )
    }
  }


  override fun subscribe() {

  }


  override fun unSubscribe() {

  }
}
package io.github.anthonyeef.cattle.presenter

import android.util.Log
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.github.anthonyeef.cattle.CattleSchedulers.io
import io.github.anthonyeef.cattle.CattleSchedulers.mainThread
import io.github.anthonyeef.cattle.contract.StatusDetailContract
import io.github.anthonyeef.cattle.service.ServiceGenerator
import io.github.anthonyeef.cattle.service.StatusService

class StatusDetailPresenter() : StatusDetailContract.Presenter {

  private lateinit var statusDetailView: StatusDetailContract.View
  private lateinit var lifecycleScopeProvider: AndroidLifecycleScopeProvider
  private val statusService = ServiceGenerator.createDefaultService(StatusService::class.java)
    private val TAG = StatusDetailPresenter::class.java.simpleName


  constructor(view: StatusDetailContract.View, lifeProvider: AndroidLifecycleScopeProvider): this() {
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
                Log.d(TAG, it.toString())
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
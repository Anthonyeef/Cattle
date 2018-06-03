package io.github.anthonyeef.cattle.contract

import io.github.anthonyeef.cattle.BasePresenter
import io.github.anthonyeef.cattle.BaseView
import io.github.anthonyeef.cattle.data.statusData.Status

interface StatusDetailContractNg {

  interface View : BaseView<Presenter> {
    fun showOriginalStatus(item: Status)
    fun showStatusContext(items: List<Status>)
  }

  interface Presenter : BasePresenter {
    fun loadOriginalStatusById(id: String)
    fun loadContextStatus(id: String)
  }
}
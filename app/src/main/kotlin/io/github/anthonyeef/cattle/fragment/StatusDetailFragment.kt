package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.contract.StatusDetailContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.view.StatusItemDetailViewProvider

/**
 *
 */
class StatusDetailFragment : BaseListFragment(), StatusDetailContract.View {

    companion object {
        const val KEY_STATUS_ID = "key_status_id"
    }

    private var statusDetailPresenter: StatusDetailContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(Status::class.java, StatusItemDetailViewProvider())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView =  super.onCreateView(inflater, container, savedInstanceState)
        disablePullToRefresh()

        return contentView
    }

    override fun showStatusDetail(item: Status) {
        items.clear()
        items.add(item)
        adapter.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        statusDetailPresenter?.subscribe()
    }


    override fun setPresenter(presenter: StatusDetailContract.Presenter?) {
        statusDetailPresenter = presenter
    }
}
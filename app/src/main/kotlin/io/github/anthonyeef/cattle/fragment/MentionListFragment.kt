package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.View
import io.github.anthonyeef.cattle.contract.MentionListContract
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.MentionListPresenter
import io.github.anthonyeef.cattle.utils.SwipeRefreshDelegate
import io.github.anthonyeef.cattle.viewbinder.StatusItemViewBinder

/**
 *
 */
class MentionListFragment : BaseListFragment(), MentionListContract.View, SwipeRefreshDelegate.OnSwipeRefreshListener {

    lateinit var mentionPresenter: MentionListContract.Presenter
    lateinit var refreshDelegate: SwipeRefreshDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(Status::class.java, StatusItemViewBinder())
        refreshDelegate = SwipeRefreshDelegate(this)
        MentionListPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshDelegate.attach(this)
    }

    override fun onResume() {
        super.onResume()
        mentionPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mentionPresenter.unSubscribe()
    }

    override fun setPresenter(presenter: MentionListContract.Presenter) {
        mentionPresenter = presenter
    }

    override fun showError(e: Throwable) {
        showException(this, e)
    }

    override fun setLoadingProgressBar(show: Boolean) {
        refreshDelegate.setRefresh(show)
    }

    override fun updateList(clearData: Boolean, data: List<Status>) {
        if (clearData) {
            adapter.setItems(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun isActivated(): Boolean {
        return isAdded
    }

    override fun onSwipeRefresh() {
        mentionPresenter.loadDataFromRemote()
    }
}
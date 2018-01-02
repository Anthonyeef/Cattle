package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.View
import io.github.anthonyeef.cattle.contract.DirectMessageContract
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.DirectMessagePresenter
import io.github.anthonyeef.cattle.entity.DirectMessage
import io.github.anthonyeef.cattle.utils.SwipeRefreshDelegate
import io.github.anthonyeef.cattle.view.DirectMessageViewProvider

/**
 *
 */
class DirectMessageInboxFragment : BaseListFragment(), DirectMessageContract.View, SwipeRefreshDelegate.OnSwipeRefreshListener {
    lateinit var directPresenter: DirectMessageContract.Presenter
    lateinit var refreshDelegate: SwipeRefreshDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.register(DirectMessage::class.java, DirectMessageViewProvider())
        refreshDelegate = SwipeRefreshDelegate(this)
        DirectMessagePresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshDelegate.attach(this)
    }

    override fun onResume() {
        super.onResume()
        directPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        directPresenter.unSubscribe()
    }

    override fun setPresenter(presenter: DirectMessageContract.Presenter) {
        directPresenter = presenter
    }

    override fun updateList(clearData: Boolean, data: List<DirectMessage>) {
        if (clearData) {
            adapter.setItems(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(e: Throwable) {
        showException(this, e)
    }

    override fun setLoadingProgressBar(show: Boolean) {
        refreshDelegate.setRefresh(show)
    }

    override fun isActivated(): Boolean {
        return isAdded
    }

    override fun onSwipeRefresh() {
        directPresenter.loadDataFromRemote()
    }
}
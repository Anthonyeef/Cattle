package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.View
import io.github.anthonyeef.cattle.contract.HomeFeedListContract
import io.github.anthonyeef.cattle.entity.BottomRefreshEntity
import io.github.anthonyeef.cattle.entity.Status
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.HomeFeedListPresenter
import io.github.anthonyeef.cattle.utils.LoadMoreDelegate
import io.github.anthonyeef.cattle.utils.SwipeRefreshDelegate
import io.github.anthonyeef.cattle.view.BottomRefreshViewProvider
import io.github.anthonyeef.cattle.view.StatusItemViewProvider
import me.drakeet.multitype.Items

/**
 *
 */
class HomeFeedListFragment : BaseListFragment(),
        HomeFeedListContract.View,
        SwipeRefreshDelegate.OnSwipeRefreshListener,
        LoadMoreDelegate.LoadMoreSubject {

    lateinit var homeFeedListPresenter: HomeFeedListContract.Presenter

    lateinit var refreshDelegate: SwipeRefreshDelegate

    lateinit var loadMoreDelegate: LoadMoreDelegate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(Status::class.java, StatusItemViewProvider())
        adapter.register(BottomRefreshEntity::class.java, BottomRefreshViewProvider())
        refreshDelegate = SwipeRefreshDelegate(this)
        loadMoreDelegate = LoadMoreDelegate(this)
        HomeFeedListPresenter(this)
    }


    override fun onResume() {
        super.onResume()
        homeFeedListPresenter.subscribe()
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshDelegate.attach(this)
        loadMoreDelegate.attach(this)
    }


    override fun onPause() {
        super.onPause()
        homeFeedListPresenter.unSubscribe()
    }


    override fun setPresenter(presenter: HomeFeedListContract.Presenter) {
        homeFeedListPresenter = presenter
    }


    override fun updateTimeline(clearData: Boolean, data: List<Status>) {
        if (clearData) {
            items = Items(data)
            adapter.items = items
            adapter.notifyDataSetChanged()
        } else {
            val tempItems = items
            tempItems.addAll(data)
            adapter.items = tempItems
            adapter.notifyDataSetChanged()
        }
    }


    override fun onSwipeRefresh() {
        homeFeedListPresenter.loadDataFromRemote(clearData = true)
    }


    override fun showError(e: Throwable) {
        showException(this, e)
    }


    override fun setLoadingProgressBar(show: Boolean) {
        refreshDelegate.setRefresh(show)
    }


    override fun scrollToTop() {
        refreshDelegate.scrollToTop()
    }


    override fun isActivated(): Boolean {
        return isAdded
    }


    override fun isLoading(): Boolean {
        return homeFeedListPresenter.isLoading()
    }


    // 虽然写了，但感觉用不到。先放着吧。
    override fun setBottomLoadingProgressBar(show: Boolean) {
        if (show) {
            if (items[items.size - 1] !is BottomRefreshEntity) {
                items.add(BottomRefreshEntity("Test"))
                adapter.notifyDataSetChanged()
            }
        } else {
            if (items[items.size - 1] is BottomRefreshEntity) {
                items.removeAt(items.size - 1)
            }
            adapter.notifyDataSetChanged()
        }
    }


    override fun onLoadMore() {
        if (!isLoading()) {
            homeFeedListPresenter.loadDataFromRemote(false)
        }
    }
}
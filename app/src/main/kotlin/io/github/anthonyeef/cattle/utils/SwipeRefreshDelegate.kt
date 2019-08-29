package io.github.anthonyeef.cattle.utils

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.fragment.BaseFragment

/**
 *
 */
class SwipeRefreshDelegate {

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var refreshListener: OnSwipeRefreshListener? = null
    private var list: RecyclerView? = null

    interface OnSwipeRefreshListener {
        fun onSwipeRefresh()
    }

    constructor(listener: OnSwipeRefreshListener) {
        refreshListener = listener
    }

    fun attach(fragment: BaseFragment) {
        swipeRefreshLayout = fragment.view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        initSwipeRefreshLayout()

        list = fragment.view?.findViewById<RecyclerView>(android.R.id.list)
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout?.setOnRefreshListener {
            refreshListener?.onSwipeRefresh()
        }
    }

    fun isShowingRefresh(): Boolean {
        return swipeRefreshLayout?.isRefreshing ?: false
    }

    fun setRefresh(isRefreshing: Boolean) {
        if (!isRefreshing) {
            swipeRefreshLayout?.postDelayed({
                swipeRefreshLayout?.isRefreshing = false
            }, 1000)
        } else {
            swipeRefreshLayout?.isRefreshing = true
        }
    }

    fun scrollToTop() {
        list?.scrollToPosition(0)
    }
}
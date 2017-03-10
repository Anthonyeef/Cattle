package io.github.anthonyeef.cattle.utils

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.fragment.BaseFragment
import org.jetbrains.anko.support.v4.findOptional

/**
 *
 */
class SwipeRefreshDelegate : CatLogger {

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var refreshListener: OnSwipeRefreshListener? = null
    private var list: RecyclerView? = null

    interface OnSwipeRefreshListener {
        fun onSwipeRefresh()
    }

    constructor(listener: OnSwipeRefreshListener) {
        refreshListener = listener
    }

    fun attach(fragment: BaseFragment) {
        swipeRefreshLayout = fragment.findOptional<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        initSwipeRefreshLayout()

        list = fragment.findOptional<RecyclerView>(android.R.id.list)
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
        list?.smoothScrollToPosition(0)
    }
}
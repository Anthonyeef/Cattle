package io.github.anthonyeef.cattle.utils

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.support.v4.findOptional

/**
 *
 */
class LoadMoreDelegate(lt: LoadMoreSubject) {
    val loadMoreSubject: LoadMoreSubject = lt
    var list: RecyclerView? = null

    interface LoadMoreSubject {
        fun isLoading(): Boolean
        fun onLoadMore()
    }

    fun attach(fragment: Fragment) {
        list = fragment.findOptional<RecyclerView>(android.R.id.list)
        val listener = EndlessScrollListener(list?.layoutManager as LinearLayoutManager, loadMoreSubject)
        list?.addOnScrollListener(listener)
    }

    inner class EndlessScrollListener(lm: LinearLayoutManager, lt: LoadMoreSubject) : RecyclerView.OnScrollListener() {
        val VISIBLE_THRESHOLD = 5
        val layoutManager: LinearLayoutManager = lm
        val loadMoreSubject: LoadMoreSubject = lt

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy < 0 || loadMoreSubject.isLoading()) return

            val itemCount = layoutManager.itemCount
            val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD
            if (isBottom) {
                loadMoreSubject.onLoadMore()
            }
        }
    }
}
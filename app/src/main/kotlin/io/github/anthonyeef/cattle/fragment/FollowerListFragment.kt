package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uber.autodispose.AutoDispose.autoDisposable
import io.github.anthonyeef.cattle.databinding.FragmentListRefreshBinding
import io.github.anthonyeef.cattle.utils.LoadMoreDelegate
import io.github.anthonyeef.cattle.utils.SimpleCountingIdlingResource
import io.github.anthonyeef.cattle.utils.SwipeRefreshDelegate
import io.github.anthonyeef.cattle.viewbinder.FollowerListItemViewBinder
import io.github.anthonyeef.cattle.viewmodel.FollowerListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.drakeet.multitype.register

/**
 *
 * Created by wuyifen on 04/03/2018.
 */
class FollowerListFragment : BaseListFragment(),
        SwipeRefreshDelegate.OnSwipeRefreshListener,
        LoadMoreDelegate.LoadMoreSubject {

    private var followerFragBinding: FragmentListRefreshBinding? = null
    private var followerListViewModel: FollowerListViewModel? = null
    private var loading: SimpleCountingIdlingResource? = null
    private lateinit var loadMoreDelegate: LoadMoreDelegate
    private lateinit var refreshDelegate: SwipeRefreshDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMoreDelegate = LoadMoreDelegate(this)
        refreshDelegate = SwipeRefreshDelegate(this)
        loading = SimpleCountingIdlingResource("FollowerList")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        followerFragBinding = FragmentListRefreshBinding.inflate(inflater, container, false)
        followerFragBinding?.view = this
        followerFragBinding?.viewmodel = followerListViewModel

        list = followerFragBinding?.list
        swipeRefreshLayout = followerFragBinding?.swipeRefreshLayout

        return followerFragBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMoreDelegate.attach(this)
        refreshDelegate.attach(this)

        refreshDelegate.setRefresh(true)
        performDataLoad()
    }


    override fun customizeRecyclerView() {
        adapter.register(FollowerListItemViewBinder())
    }


    override fun isLoading(): Boolean {
        return loading?.isIdleNow?.not() ?: false
    }


    override fun onLoadMore() {
        loading?.increment()

        followerListViewModel?.let {
            it.loadMoreFollowerList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        if (loading?.isIdleNow?.not() == true) {
                            loading?.decrement()
                        }
                    }
                    .`as`(autoDisposable(lifeScope))
                    .subscribe(
                            {
                                val position = items.size
                                items.addAll(it)
                                adapter.notifyItemInserted(position)
                            },
                            {
                                // todo
                            }
                    )
        }
    }


    override fun onSwipeRefresh() {
        performDataLoad()
    }


    private fun performDataLoad() {
        loading?.increment()
        followerListViewModel?.let {
            it.loadFollowerList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        if (loading?.isIdleNow?.not() == true) {
                            loading?.decrement()
                        }
                        refreshDelegate.setRefresh(false)
                    }
                    .`as`(autoDisposable(lifeScope))
                    .subscribe(
                            {
                                items.clear()
                                items.addAll(it)
                                adapter.notifyDataSetChanged()
                            },
                            {
                                // todo:
                            }
                    )
        }
    }


    fun setViewModel(viewmodel: FollowerListViewModel) {
        followerListViewModel = viewmodel
    }
}
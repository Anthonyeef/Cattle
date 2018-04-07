package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.contract.ProfileAlbumContract
import io.github.anthonyeef.cattle.data.source.album.AlbumRepository
import io.github.anthonyeef.cattle.data.source.remote.AlbumRemoteDataSource
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.entity.EmptyHintEntity
import io.github.anthonyeef.cattle.exception.showException
import io.github.anthonyeef.cattle.presenter.ProfileAlbumPresenter
import io.github.anthonyeef.cattle.utils.LoadMoreDelegate
import io.github.anthonyeef.cattle.viewbinder.EmptyHintViewBinder
import io.github.anthonyeef.cattle.viewbinder.ProfileAlbumItemViewBinder

/**
 *
 */
class ProfileAlbumFragment : BaseListFragment(), ProfileAlbumContract.View, LoadMoreDelegate.LoadMoreSubject {

    private var mAlbumPresenter: ProfileAlbumContract.Presenter? = null
    private val userId: String by lazy { arguments?.getString(KEY_USER_ID, "") ?: "" }
    private lateinit var loadMoreDelegate: LoadMoreDelegate

    companion object {
        const val KEY_USER_ID = "key_user_id"
        const val SPAN_SIZE = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.register(Status::class.java, ProfileAlbumItemViewBinder())
        adapter.register(EmptyHintEntity::class.java, EmptyHintViewBinder())
        loadMoreDelegate = LoadMoreDelegate(this)
        ProfileAlbumPresenter(this,
            AlbumRepository.getInstance(AlbumRemoteDataSource.getInstance()),
            userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMoreDelegate.attach(this)
        mAlbumPresenter?.loadPhotos()
    }

    override fun onResume() {
        super.onResume()

        mAlbumPresenter?.subscribe()
    }


    override fun onPause() {
        super.onPause()

        mAlbumPresenter?.unSubscribe()
    }


    override fun customizeRecyclerView() {
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (items[position]) {
                    is Status -> 1
                    else -> 3
                }
            }
        }

        val layoutManager = GridLayoutManager(context, SPAN_SIZE)
        layoutManager.spanSizeLookup = spanSizeLookup

        list?.layoutManager = layoutManager
    }


    override fun setPresenter(presenter: ProfileAlbumContract.Presenter?) {
        mAlbumPresenter = presenter
    }


    override fun showPhotoStatus(clearData: Boolean, data: List<Status>) {
        if (clearData) {
            items.clear()
        }
        val notifyPosition = items.size
        items.addAll(data)
        adapter.notifyItemInserted(notifyPosition)
    }


    override fun showError(error: Throwable) {
        showException(error)
    }


    override fun showEmptyView() {
        val notifyPosition = items.size
        if (items.isEmpty()) {
            items.add(EmptyHintEntity(R.string.hint_no_album_photos))
            adapter.notifyItemInserted(notifyPosition)
        } else {
            items.add(EmptyHintEntity(R.string.hint_no_more_album_photos))
            adapter.notifyItemInserted(notifyPosition)
        }
    }


    override fun onError() {
        // todo
    }


    override fun onLoadMore() {
        if (mAlbumPresenter?.hasMorePhotos() == true) {
            mAlbumPresenter?.loadPhotos()
        }
    }


    override fun isLoading(): Boolean {
        return mAlbumPresenter?.isAlbumDataLoading() ?: false
    }
}
package io.github.anthonyeef.cattle.presenter

import io.github.anthonyeef.cattle.contract.ProfileAlbumContract
import io.github.anthonyeef.cattle.data.source.album.AlbumRepository
import io.github.anthonyeef.cattle.utils.EspressoIdlingResource
import io.github.anthonyeef.cattle.utils.SimpleCountingIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class ProfileAlbumPresenter() : ProfileAlbumContract.Presenter {

    private lateinit var mProfileAlbumView: ProfileAlbumContract.View
    private lateinit var userId: String
    private lateinit var albumRepo: AlbumRepository
    private lateinit var loading: SimpleCountingIdlingResource
    private var lastStatusId: String = ""
    private val _disposable: CompositeDisposable = CompositeDisposable()
    private var hasMore = true

    constructor(view: ProfileAlbumContract.View,
                albumRepository: AlbumRepository,
                userIdData: String): this() {
        albumRepo = albumRepository
        userId = userIdData
        mProfileAlbumView = view
        mProfileAlbumView.setPresenter(this)
        loading = SimpleCountingIdlingResource("ProfileAlbum")
    }


    override fun subscribe() {

    }


    override fun unSubscribe() {
        _disposable.clear()
    }


    override fun isAlbumDataLoading(): Boolean {
        return loading.isIdleNow.not()
    }


    override fun loadPhotos() {
        EspressoIdlingResource.increment() // App is busy until further notice
        loading.increment()

        _disposable.add(albumRepo.getAlbumPhotos(userId, lastStatusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (EspressoIdlingResource.getIdlingResource().isIdleNow.not()) {
                        EspressoIdlingResource.decrement()
                    }

                    if (loading.isIdleNow.not()) {
                        loading.decrement()
                    }
                }
                .subscribe(
                        {
                            if (it.isNotEmpty()) {
                                if (it.first().id == lastStatusId) {
                                    mProfileAlbumView.showPhotoStatus(clearData = lastStatusId.isBlank(), data = it.filterNot { it.id == lastStatusId })
                                } else {
                                    mProfileAlbumView.showPhotoStatus(clearData = lastStatusId.isBlank(), data = it)
                                }

                                if (it.last().id == lastStatusId) {
                                    hasMore = false
                                    mProfileAlbumView.showEmptyView()
                                } else {
                                    lastStatusId = it.last().id
                                    if (lastStatusId.contentEquals("false")) {
                                        hasMore = false
                                        mProfileAlbumView.showEmptyView()
                                    }
                                }
                            } else {
                                hasMore = false
                                mProfileAlbumView.showEmptyView()
                            }
                        },
                        {
                            mProfileAlbumView.showError(error = it)
                        }
                ))
    }


    override fun hasMorePhotos(): Boolean {
        return hasMore
    }
}
package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import io.github.anthonyeef.cattle.contract.FollowersListContract

/**
 *
 */
class FollowersListFragment : BaseListFragment(), FollowersListContract.View {
    private var followerListPresenter: FollowersListContract.Presenter? = null

    override fun setPresenter(presenter: FollowersListContract.Presenter?) {
        followerListPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
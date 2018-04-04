package io.github.anthonyeef.cattle.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.data.source.follower.FollowerRemoteDataSource
import io.github.anthonyeef.cattle.data.source.follower.FollowerRepository
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.fragment.FollowerListFragment
import io.github.anthonyeef.cattle.viewmodel.FollowerListViewModel
import org.jetbrains.anko.doFromSdk

/**
 * Created by wuyifen on 04/03/2018.
 */
class FollowerListActivity : BaseActivity() {

    companion object {
        @JvmField val KEY_USER_ID = "key_user_id"
    }

    private var followerListViewModel: FollowerListViewModel? = null
    private var followerListFragment: FollowerListFragment? = null
    private val userId by lazy { intent.extras.getString(KEY_USER_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_toolbar_general)

        followerListViewModel = createViewModel()
        followerListFragment = findOrCreateViewFragment()

        followerListViewModel?.let {
            followerListFragment?.setViewModel(it)
        }

        doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }

        setToolbarTitle(getString(R.string.title_follower_list))
    }


    private fun createViewModel(): FollowerListViewModel {
        val followerListRemoteDataSource = FollowerRemoteDataSource.getInstance()
        val followerListRepository = FollowerRepository.getInstance(followerListRemoteDataSource)
        return FollowerListViewModel(followerListRepository, userId)
    }


    private fun findOrCreateViewFragment(): FollowerListFragment {
        var fragment = supportFragmentManager.findFragmentById(R.id.container) as? FollowerListFragment
        if (fragment == null) {
            fragment = FollowerListFragment()

            bindFragment(fragment)
        }

        return fragment
    }
}
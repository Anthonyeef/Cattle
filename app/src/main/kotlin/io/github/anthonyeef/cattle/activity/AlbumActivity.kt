package io.github.anthonyeef.cattle.activity

import android.os.Bundle
import io.github.anthonyeef.cattle.extension.bindFragment
import io.github.anthonyeef.cattle.extension.withArguments
import io.github.anthonyeef.cattle.fragment.ProfileAlbumFragment

/**
 *
 */
class AlbumActivity : BaseActivity() {

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*verticalLayout {
            id = CONTAINER_ID
            lparams(width = matchParent, height = matchParent)
        }*/


        setupAlbumFragment()
    }


    private fun setupAlbumFragment() {
        val userInfoId = intent.getStringExtra(EXTRA_USER_ID)
        userInfoId?.let {
            val albumFragment = ProfileAlbumFragment().withArguments(ProfileAlbumFragment.KEY_USER_ID to it)
            bindFragment(albumFragment)
        }
    }
}
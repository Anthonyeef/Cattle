package io.github.anthonyeef.cattle.databinding

import android.content.Intent
import io.github.anthonyeef.cattle.activity.PhotoDisplayActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.extension.withArguments
import io.github.anthonyeef.cattle.fragment.ProfileFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment

/**
 *
 */
object StatusItemClickEventHandler {
    fun onClickStatus(status: Status) {
      StatusDetailFragment()
          .withArguments(StatusDetailFragment.KEY_STATUS_ID to status.id)
          .show()
    }

    fun openProfile(status: Status) {
        ProfileFragment()
            .withArguments(ProfileFragment.KEY_USER_ID to status.user?.id)
            .show(tintStatusBar = false)
    }

    fun openPhotoDisplay(status: Status) {
        val intent = Intent(app, PhotoDisplayActivity::class.java)
        intent.putExtra(PhotoDisplayActivity.KEY_IMAGE_URL, status.photo?.largeurl)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }
}
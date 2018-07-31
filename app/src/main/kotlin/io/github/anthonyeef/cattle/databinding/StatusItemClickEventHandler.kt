package io.github.anthonyeef.cattle.databinding

import io.github.anthonyeef.cattle.activity.PhotoDisplayActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.fragment.ProfileFragment
import io.github.anthonyeef.cattle.fragment.StatusDetailFragment
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.withArguments

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
        app.startActivity(app.intentFor<PhotoDisplayActivity>(PhotoDisplayActivity.KEY_IMAGE_URL to status.photo?.largeurl).newTask())
    }
}
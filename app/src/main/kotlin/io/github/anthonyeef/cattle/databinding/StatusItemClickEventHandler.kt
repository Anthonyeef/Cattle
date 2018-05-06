package io.github.anthonyeef.cattle.databinding

import io.github.anthonyeef.cattle.activity.PhotoDisplayActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_HAS_CONVERSATION
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_ID
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.extension.show
import io.github.anthonyeef.cattle.fragment.ProfileFragment
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.withArguments

/**
 *
 */
object StatusItemClickEventHandler {
    fun onClickStatus(status: Status) {
        app.startActivity(app.intentFor<StatusDetailActivity>(EXTRA_STATUS_ID to status.id, EXTRA_STATUS_HAS_CONVERSATION to status.inReplyToStatusId.isNotEmpty()).newTask())
    }

    fun openProfile(status: Status) {
        ProfileFragment()
            .withArguments(ProfileFragment.KEY_USER_ID to status.user?.id)
            .show()
    }

    fun openPhotoDisplay(status: Status) {
        app.startActivity(app.intentFor<PhotoDisplayActivity>(PhotoDisplayActivity.KEY_IMAGE_URL to status.photo?.largeurl).newTask())
    }
}
package io.github.anthonyeef.cattle.databinding

import io.github.anthonyeef.cattle.activity.ProfileActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_HAS_CONVERSATION
import io.github.anthonyeef.cattle.activity.StatusDetailActivity.Companion.EXTRA_STATUS_ID
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.data.statusData.Status
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

/**
 *
 */
object StatusItemClickEventHandler {
    fun onClickStatus(status: Status) {
        app.startActivity(app.intentFor<StatusDetailActivity>(EXTRA_STATUS_ID to status.id, EXTRA_STATUS_HAS_CONVERSATION to status.inReplyToStatusId.isNotEmpty()).newTask())
    }

    fun openProfile(status: Status) {
        app.startActivity(app.intentFor<ProfileActivity>(ProfileActivity.EXTRA_USER_ID to status.user?.id).newTask())
    }
}
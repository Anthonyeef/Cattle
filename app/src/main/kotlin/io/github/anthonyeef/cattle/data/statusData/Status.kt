package io.github.anthonyeef.cattle.data.statusData

import android.arch.persistence.room.*
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.entity.Photo

/**
 * A fanfou status.
 */
@Entity
data class Status(

    @PrimaryKey
    var rawid: Int = 0,

    @ColumnInfo(name = "status_id")
    var id: String = "",

    @ColumnInfo
    var text: String = "",

    @ColumnInfo
    var source: String = "",

    @ColumnInfo(name = "status_created_at")
    var createdAt: String = "",

    @ColumnInfo
    var truncated: Boolean = false,

    @ColumnInfo
    var favorited: Boolean = false,

    @ColumnInfo(name = "in_reply_to_status_id")
    var inReplyToStatusId: String = "",

    @ColumnInfo(name = "in_reply_to_user_id")
    var inReplyToUserId: String = "",

    @ColumnInfo(name = "in_reply_to_screen_name")
    var inReplyToScreenName: String = "",

    @ColumnInfo(name = "repo_status_id")
    var repostStatusId: String = "",

    @ColumnInfo(name = "repost_user_id")
    var repostUserId: String = "",

    @ColumnInfo(name = "repost_screen_name")
    var repostScreenName: String = "",

    @ColumnInfo(name = "status_location")
    var location: String = "",

    @Ignore
    var repostStatus: EmbeddedStatus? = null,

    @Embedded
    var user: UserInfo? = null,

    @Embedded
    var photo: Photo? = null,

    @Ignore
    var isSingle: Boolean = true
)
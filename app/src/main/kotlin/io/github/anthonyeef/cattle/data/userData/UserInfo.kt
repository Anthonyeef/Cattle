package io.github.anthonyeef.cattle.data.userData

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import io.github.anthonyeef.cattle.data.statusData.Status

/**
 * UserInfo entity.
 */
@Entity
class UserInfo {

    @PrimaryKey
    var id: String = ""

    @ColumnInfo
    var name: String = ""

    @ColumnInfo(name="screen_name")
    var screenName: String = ""

    @ColumnInfo(name = "profile_image_url")
    var profileImageUrl: String = ""

    @ColumnInfo(name = "profile_image_url_large")
    var profileImageUrlLarge: String = ""

    @ColumnInfo
    var location: String = ""

    @ColumnInfo
    var gender: String = ""

    @ColumnInfo
    var birthday: String = ""

    @ColumnInfo
    var description: String = ""

    @ColumnInfo
    var url: String = ""

    @ColumnInfo
    var protected: Boolean = false

    @ColumnInfo(name = "followers_count")
    var followersCount: Int = 0

    @ColumnInfo(name = "friends_count")
    var friendsCount: Int = 0

    @ColumnInfo(name = "favourites_count")
    var favouritesCount: Int = 0

    @ColumnInfo(name = "statuses_count")
    var statusesCount: Int = 0

    @ColumnInfo
    var following: Boolean = false

    @ColumnInfo
    var notifications: Boolean = false

    @ColumnInfo(name = "user_created_at")
    var createdAt: String = ""

    @ColumnInfo(name = "utc_offset")
    var utcOffset: Int = 0

    @ColumnInfo(name = "profile_background_color")
    var profileBackgroundColor = ""

    @ColumnInfo(name = "profile_background_image_url")
    var profileBackgroundImageUrl = ""

    @Ignore
    var status: Status? = null
}
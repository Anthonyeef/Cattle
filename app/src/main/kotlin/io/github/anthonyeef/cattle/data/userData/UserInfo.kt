package io.github.anthonyeef.cattle.data.userData

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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

    @ColumnInfo(name = "statuses_count")
    var statusesCount: Int = 0

    @ColumnInfo
    var following: Boolean = false
}
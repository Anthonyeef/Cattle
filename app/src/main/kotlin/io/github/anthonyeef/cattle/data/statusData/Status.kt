package io.github.anthonyeef.cattle.data.statusData

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.entity.Photo

/**
 * A fanfou status.
 */
@Entity
class Status {

    @PrimaryKey
    var rawid: Int = 0

    @ColumnInfo(name = "status_id")
    var id: String = ""

    @ColumnInfo
    var text: String = ""

    @ColumnInfo
    var source: String = ""

    @ColumnInfo(name = "created_at")
    var createdAt: String = ""

    @Embedded
    var user: UserInfo? = null

    @Embedded
    var photo: Photo? = null
}
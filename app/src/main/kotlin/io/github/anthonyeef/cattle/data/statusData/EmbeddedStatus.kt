package io.github.anthonyeef.cattle.data.statusData

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 *
 */
@Entity(tableName = "embedded_status",
        foreignKeys = arrayOf(
                ForeignKey(entity = Status::class,
                        parentColumns = arrayOf("rawid"),
                        childColumns = arrayOf("owner_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        ))
class EmbeddedStatus {
    @PrimaryKey
    var id: String = ""

    @ColumnInfo(name = "owner_id", index = true)
    var ownerId: Int? = null
}
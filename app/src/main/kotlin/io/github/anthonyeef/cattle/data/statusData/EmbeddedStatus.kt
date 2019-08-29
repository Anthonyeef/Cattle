package io.github.anthonyeef.cattle.data.statusData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 *
 */
@Entity(tableName = "embedded_status",
        foreignKeys = [(ForeignKey(entity = Status::class,
                parentColumns = arrayOf("rawid"),
                childColumns = arrayOf("owner_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))])
class EmbeddedStatus {
    @PrimaryKey
    var id: String = ""

    @ColumnInfo(name = "owner_id", index = true)
    var ownerId: Int? = null
}
package io.github.anthonyeef.cattle.entity

import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import io.github.anthonyeef.cattle.data.CattleDatabase

/**
 *
 */
@Table(database = CattleDatabase::class)
class Status : BaseModel() {

    @PrimaryKey
    @Column
    var id: String = ""

    @Index
    @Column(name = "raw_id")
    var rawid: Int = 0

    @Column
    var text: String = ""

    var source: String = ""

    @ForeignKey(saveForeignKeyModel = true)
    var user: UserInfo? = null

    var location: String = ""

    @ForeignKey(saveForeignKeyModel = true)
    var photo: Photo? = null

    @Column(name = "create_at")
    var createdAt: String = ""
}
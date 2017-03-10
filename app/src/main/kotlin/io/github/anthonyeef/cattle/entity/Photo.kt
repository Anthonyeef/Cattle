package io.github.anthonyeef.cattle.entity

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import io.github.anthonyeef.cattle.data.CattleDatabase

/**
 *
 */
@Table(database = CattleDatabase::class)
class Photo : BaseModel() {
    @PrimaryKey
    @Column
    var thumburl: String = ""
    @Column
    var imageurl: String = ""
    @Column
    var largeurl: String = ""
}
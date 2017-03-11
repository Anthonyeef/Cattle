package io.github.anthonyeef.cattle.entity

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import io.github.anthonyeef.cattle.data.CattleDatabase

/**
 * User information model.
 */
@Table(database = CattleDatabase::class)
class UserInfo : BaseModel() {
    @PrimaryKey
    @Column
    var id: String = ""

    @Column
    var name: String? = null

    @Column(name = "screen_name")
    var screenName: String? = null

    var profileImageUrl: String = ""

    @Column(name = "profile_image_url_large")
    var profileImageUrlLarge: String = ""

    var location: String = ""
    var gender: String = ""
    var birthday: String = ""
    var description: String = ""
    var url: String = ""
    var protected: Boolean = false
    var followersCount: Int = 0
    var friendsCount: Int = 0
    var favouritesCount: Int = 0
    var statusesCount: Int = 0
    var following: Boolean = false
}

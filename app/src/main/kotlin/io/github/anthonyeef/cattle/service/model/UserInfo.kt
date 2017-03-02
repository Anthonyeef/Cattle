package io.github.anthonyeef.cattle.service.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * User information model.
 */
open class UserInfo : RealmObject() {
    @PrimaryKey open var id: String = ""
    open var name: String? = null
    open var screenName: String? = null
    open var profileImageUrl: String = ""
    open var profileImageUrlLarge: String = ""
    open var location: String = ""
    open var gender: String = ""
    open var birthday: String = ""
    open var description: String = ""
    open var url: String = ""
    open var protected: Boolean = false
    open var followersCount: Int = 0
    open var friendsCount: Int = 0
    open var favouritesCount: Int = 0
    open var statusesCount: Int = 0
    open var following: Boolean = false
}

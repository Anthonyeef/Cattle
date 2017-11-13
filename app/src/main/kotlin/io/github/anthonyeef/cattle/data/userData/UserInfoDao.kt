package io.github.anthonyeef.cattle.data.userData

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 *
 */
@Dao
interface UserInfoDao {

    @Query("SELECT * FROM userInfo WHERE id LIKE :id")
    fun getUserInfoById(id: String): UserInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)
}
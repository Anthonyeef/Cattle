package io.github.anthonyeef.cattle.data.userData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 */
@Dao
interface UserInfoDao {

    @Query("SELECT * FROM userInfo WHERE id LIKE :userId")
    fun loadUserInfoSync(userId: String): UserInfo

    @Query("SELECT * FROM userInfo WHERE id LIKE :userId")
    fun loadUserInfo(userId: String): LiveData<UserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)
}
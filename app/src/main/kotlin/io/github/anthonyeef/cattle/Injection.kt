package io.github.anthonyeef.cattle

import io.github.anthonyeef.cattle.data.AppDatabase
import io.github.anthonyeef.cattle.data.statusData.StatusDao
import io.github.anthonyeef.cattle.data.userData.UserInfoDao

/**
 *
 */
object Injection {

    fun provideStatusDao(): StatusDao {
        return AppDatabase.getInstance(App.get()).statusDao()
    }


    fun provideUserInfoDao(): UserInfoDao {
        return AppDatabase.getInstance(App.get()).userInfoDao()
    }
}
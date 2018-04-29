package io.github.anthonyeef.cattle

import io.github.anthonyeef.cattle.data.AppDatabase
import io.github.anthonyeef.cattle.data.statusData.StatusDao
import io.github.anthonyeef.cattle.data.userData.UserInfoDao

/**
 *
 */
object Injection {
    val statusDb = AppDatabase.getInstance(App.get()).statusDao()
    val userInfoDb = AppDatabase.getInstance(App.get()).userInfoDao()
}
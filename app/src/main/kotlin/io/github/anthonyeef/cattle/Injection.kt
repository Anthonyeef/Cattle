package io.github.anthonyeef.cattle

import io.github.anthonyeef.cattle.data.AppDatabase

/**
 *
 */
object Injection {
    val statusDb = AppDatabase.getInstance(App.get()).statusDao()
    val userInfoDb = AppDatabase.getInstance(App.get()).userInfoDao()
}
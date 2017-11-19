package io.github.anthonyeef.cattle.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.anthonyeef.cattle.App
import io.github.anthonyeef.cattle.data.statusData.EmbeddedStatus
import io.github.anthonyeef.cattle.data.statusData.Status
import io.github.anthonyeef.cattle.data.statusData.StatusDao
import io.github.anthonyeef.cattle.data.userData.UserInfo
import io.github.anthonyeef.cattle.data.userData.UserInfoDao

/**
 *
 */
@Database(entities = arrayOf(UserInfo::class, Status::class, EmbeddedStatus::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

    abstract fun statusDao(): StatusDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        @JvmStatic fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(App.get(),
                                AppDatabase::class.java, "Cattle.db")
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}

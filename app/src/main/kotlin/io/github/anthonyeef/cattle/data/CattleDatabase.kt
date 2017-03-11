package io.github.anthonyeef.cattle.data

import com.raizlabs.android.dbflow.annotation.Database

/**
 *
 */
@Database(name = CattleDatabase.NAME, version = CattleDatabase.VERSION, generatedClassSeparator = "_")
object CattleDatabase {
    const val NAME: String = "cattle"
    const val VERSION: Int = 1
}

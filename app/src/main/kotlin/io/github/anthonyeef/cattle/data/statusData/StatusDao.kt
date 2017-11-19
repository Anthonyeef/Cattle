package io.github.anthonyeef.cattle.data.statusData

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 *
 */
@Dao
interface StatusDao {

    @Query("SELECT * FROM Status ORDER BY rawid DESC LIMIT 40")
    fun getStatus(): List<Status>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatuses(statuses: List<Status>)
}
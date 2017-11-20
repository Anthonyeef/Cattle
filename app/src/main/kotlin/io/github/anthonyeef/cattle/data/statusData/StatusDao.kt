package io.github.anthonyeef.cattle.data.statusData

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * [Status] data access object class
 */
@Dao
interface StatusDao {

    @Query("SELECT * FROM Status ORDER BY rawid DESC LIMIT 40")
    fun getStatus(): List<Status>


    @Query("SELECT * FROM Status WHERE status_id LIKE :id")
    fun getStatusById(id: String): Flowable<Status>


    @Query("SELECT * FROM embedded_status WHERE owner_id LIKE :id")
    fun getRepostStatus(id: String): Flowable<EmbeddedStatus>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatuses(statuses: List<Status>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepostStatus(status: EmbeddedStatus)
}
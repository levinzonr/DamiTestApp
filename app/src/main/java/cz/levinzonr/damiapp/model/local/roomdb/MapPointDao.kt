package cz.levinzonr.damiapp.model.local.roomdb

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import cz.levinzonr.damiapp.model.entities.MapPoint
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

@Dao
interface MapPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(point: MapPoint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(points: List<MapPoint>)

    @Query("SELECT * FROM mappoint WHERE userId LIKE :userId")
    fun findPointsOfUser(userId: Int) : Flowable<List<MapPoint>>

    @Query("SELECT * FROM mappoint")
    fun findAll() : Flowable<List<MapPoint>>
}
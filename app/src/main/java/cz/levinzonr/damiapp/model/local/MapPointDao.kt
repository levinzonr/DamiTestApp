package cz.levinzonr.damiapp.model.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import cz.levinzonr.damiapp.model.entities.MapPoint
import io.reactivex.Flowable

@Dao
interface MapPointDao {

    @Insert
    fun insert(point: MapPoint)

    @Insert
    fun insertAll(points: List<MapPoint>)

    @Query("SELECT * FROM mappoint WHERE userId LIKE :userId")
    fun findPointsOfUser(userId: Int) : Flowable<List<MapPoint>>

}
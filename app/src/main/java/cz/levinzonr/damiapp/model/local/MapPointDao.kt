package cz.levinzonr.damiapp.model.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import cz.levinzonr.damiapp.model.entities.MapPoint

@Dao
interface MapPointDao {

    @Insert
    fun insert(point: MapPoint)

    @Insert
    fun insertAll(points: ArrayList<MapPoint>)

}
package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class MapPoint(

        @PrimaryKey
        val id: Int,
        val lat: Double,
        val lng: Double,
        val title: String,
        val desc: String,
        val photo: ArrayList<String>
)
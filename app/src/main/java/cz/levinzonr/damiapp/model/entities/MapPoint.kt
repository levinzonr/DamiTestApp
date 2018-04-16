package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

@Entity(foreignKeys = [ForeignKey(entity = User::class,
        childColumns = ["userId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE)])
class MapPoint(
        @PrimaryKey
        var id: Int,
        var userId: Int? = null,
        var lat: Double,
        var lng: Double,
        var title: String,
        var desc: String,
        var photo: ArrayList<String>
)


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
        val id: Int,
        var userId: Int,
        val lat: Double,
        val lng: Double,
        val title: String,
        val desc: String,

        @TypeConverters(Converters::class)
        val photo: ArrayList<String>
) {

     class Converters {

        @TypeConverter
        fun listToString(list: ArrayList<String>) : String {
            return Gson().toJson(list)
        }

        @TypeConverter
        fun stringToList(string: String?) : ArrayList<String> {
            if (string == null)
                return ArrayList()

            val listType = object : TypeToken<ArrayList<String>>() {}.type
            return Gson().fromJson(string, listType)
        }
    }

}


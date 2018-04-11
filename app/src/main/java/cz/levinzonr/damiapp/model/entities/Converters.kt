package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

        @TypeConverter
         fun listToString(list: ArrayList<String>?) : String {
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
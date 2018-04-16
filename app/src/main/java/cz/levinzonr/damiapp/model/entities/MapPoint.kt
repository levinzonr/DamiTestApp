package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
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
        var desc: String
) : Parcelable {
        constructor(parcel: Parcel)  : this(
                parcel.readInt(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readString(),
                parcel.readString()

        ) {
                parcel.readStringList(photo)
        }

        var photo: ArrayList<String> = ArrayList()

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeValue(userId)
                parcel.writeDouble(lat)
                parcel.writeDouble(lng)
                parcel.writeString(title)
                parcel.writeString(desc)
                parcel.writeStringList(photo)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<MapPoint> {
                override fun createFromParcel(parcel: Parcel): MapPoint {
                        return MapPoint(parcel)
                }

                override fun newArray(size: Int): Array<MapPoint?> {
                        return arrayOfNulls(size)
                }
        }
}


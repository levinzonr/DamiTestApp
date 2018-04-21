package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
 class User(
        @PrimaryKey
         var id: Int,
        var email: String,
        var name: String? = null,
        var lastname: String? = null,
        var description: String? = null,
        var rights: String,
        var photo: String? = null,
        var phone: String? = null,

        @Ignore
         var favorites: List<MapPoint>,

        @Ignore
         var token: String
) {
    init {
        for (point in favorites) {
            point.userId = id
        }
    }

    constructor() : this(-1, "mail", null,null,null, "admin", null,null, ArrayList<MapPoint>(), "")

    fun displayName() : String{
        return "${name ?: ""} ${lastname ?: ""}"
    }

}



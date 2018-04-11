package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity
 class User(
         @PrimaryKey
         var id: Int,
         var email: String,
         var name: String? = null,
         var lastName: String? = null,
         var description: String? = null,
         var rights: String,
         var photo: String? = null,

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

    constructor() : this(-1, "mail", null,null,null, "admin", null, ArrayList<MapPoint>(), "")

}



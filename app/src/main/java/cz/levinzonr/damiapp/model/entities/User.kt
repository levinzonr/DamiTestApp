package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity
 class User(

         @PrimaryKey
         val id: Int,
         val email: String,
         val name: String?,
         val lastName: String?,
         val description: String?,
         val rights: String,
         val photo: String?,

         @Relation(parentColumn = "id", entityColumn = "userId")
         val favorites: ArrayList<MapPoint>,

         @Ignore
         val token: String
) {
    init {
        for (point in favorites) {
            point.userId = id
        }
    }
}

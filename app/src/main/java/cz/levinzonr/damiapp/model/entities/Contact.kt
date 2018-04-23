package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Contact(
        @PrimaryKey
        var id: Int? = null,
        var email: String? = "",
        var name: String? = "",
        var lastname: String? = "",
        var description: String? = "",
        var photo: String? = null,
        var phone: String? = null
) {

    fun displayName(): String? {
        return if (name == null && lastname == null)
            null
        else "$name $lastname"
    }
}
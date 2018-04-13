package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Contact(
        @PrimaryKey
        var id: Int? = null,
        var email: String? = null,
        var name: String? = null,
        var lastname: String? = null,
        var description: String? = null,
        var photo: String? = null
)
package cz.levinzonr.damiapp.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Contact(
        @PrimaryKey
        val id: Int,
        val email: String,
        val name: String?,
        val lastname: String?,
        val description: String?,
        val photo: String?
)
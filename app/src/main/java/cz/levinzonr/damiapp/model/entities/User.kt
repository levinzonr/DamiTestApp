package cz.levinzonr.damiapp.model.entities

 class User(
         val id: Int,
         val email: String,
         val name: String,
         val lastName: String,
         val description: String,
         val rights: Array<String>,
         val photo: String,
         val favorites: ArrayList<MapPoint>,
         val token: String
)

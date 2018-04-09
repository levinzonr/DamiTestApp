package cz.levinzonr.damiapp.model.entities

class MapPoint(
        val id: Int,
        val lat: Double,
        val lng: Double,
        val title: String,
        val desc: String,
        val photo: ArrayList<String>
)
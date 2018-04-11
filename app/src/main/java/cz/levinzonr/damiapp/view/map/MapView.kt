package cz.levinzonr.damiapp.view.map

import cz.levinzonr.damiapp.model.entities.MapPoint

interface MapView {

    fun onPointsLoaded(points: ArrayList<MapPoint>)

    fun onPointsLoadingStarted()

    fun onPointsLoadingError(e: String)
}
package cz.levinzonr.damiapp.view.map


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import cz.levinzonr.damiapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.presenter.MapPresenter


class MapsFragment : SupportMapFragment(), OnMapReadyCallback, MapView{

    private lateinit var map: GoogleMap
    private lateinit var presenter: MapPresenter
    companion object {
        const val TAG = "Maps fragment"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MapPresenter()
        presenter.attachView(this)
        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            this.map = map
            this.map.setInfoWindowAdapter(MarkerInfoAdapter(context))
            this.map.setOnInfoWindowClickListener {
                val point = it.tag as MapPoint
                PointDetailActivity.startAsIntent(context, point)
            }
            presenter.getPointsOnMap()
            Log.d(TAG, "Map loaded")
        }
    }

    override fun onLoadingFinished(result: ArrayList<MapPoint>) {
        Toast.makeText(context, "Loaded ${result.size}" , Toast.LENGTH_SHORT).show()
        for (point in result) {
            val pos = LatLng(point.lat, point.lng)
            val marker = MarkerOptions()
                    .position(pos)
                    .title(point.title)
           map.addMarker(marker).tag = point

        }
        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(result.last().lat, result.last().lng)))
    }



    override fun onLoadingStarted() {
        Toast.makeText(context, "Loadin started", Toast.LENGTH_SHORT).show()
    }

    override fun onLoadingError(e: String) {
        Toast.makeText(context, "Loadin error: $e", Toast.LENGTH_SHORT).show()

    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

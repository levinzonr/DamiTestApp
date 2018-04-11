package cz.levinzonr.damiapp.view.map


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
        getMapAsync(this)
        presenter = MapPresenter()
        presenter.attachView(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            this.map = map
            Log.d(TAG, "Map loaded")
        }
        val sydney = LatLng(-34.0, 151.0)
        map?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onPointsLoaded(points: ArrayList<MapPoint>) {
        Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show()

    }

    override fun onPointsLoadingStarted() {
        Toast.makeText(context, "Loadin started", Toast.LENGTH_SHORT).show()
    }

    override fun onPointsLoadingError(e: String) {
        Toast.makeText(context, "Loadin started", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

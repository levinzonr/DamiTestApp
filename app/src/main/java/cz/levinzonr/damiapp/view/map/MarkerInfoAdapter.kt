package cz.levinzonr.damiapp.view.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.MapPoint
import kotlinx.android.synthetic.main.content_edit_contact.*
import kotlinx.android.synthetic.main.marker_info_view.view.*

class MarkerInfoAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    private val layoutInflater = LayoutInflater.from(context)
    override fun getInfoContents(p0: Marker?): View {
        return layoutInflater.inflate(R.layout.marker_info_view, null)
    }

    override fun getInfoWindow(p0: Marker?): View {
      val view = layoutInflater.inflate(R.layout.marker_info_view, null)
        view.marker_title.text = p0?.title
        Picasso.get().load((p0?.tag as MapPoint).photo.first())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(view.marker_image)
        return view
    }
}
package cz.levinzonr.damiapp.view.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.MapPoint
import kotlinx.android.synthetic.main.activity_point_detail.*
import kotlinx.android.synthetic.main.content_point_detail.*

class PointDetailActivity : AppCompatActivity() {

    companion object {

        private const val ARG_POINT = "MapPoint"

        fun startAsIntent(context: Context, point: MapPoint) {
            val intent = Intent(context, PointDetailActivity::class.java)
            intent.putExtra(ARG_POINT, point)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val point = intent.getParcelableExtra<MapPoint>(ARG_POINT)
        updateView(point)

    }

    private fun updateView(mapPoint: MapPoint) {
        point_desc.text = mapPoint.desc
        supportActionBar?.title = mapPoint.title
        Picasso.get().load(mapPoint.photo.first()).into(point_image)
    }
}

package cz.levinzonr.damiapp.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.model.local.DamiLocalDatasource
import cz.levinzonr.damiapp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_signed_in.*
import kotlinx.android.synthetic.main.app_bar_signed_in.*
import kotlinx.android.synthetic.main.nav_header_signed_in.*

class SignedInActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    private lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)
        setSupportActionBar(toolbar)
        presenter = MainPresenter()
        presenter.attachView(this)
        presenter.getUserInfo()
        val local = DamiLocalDatasource(this)

        if (!local.isLoggedIn()) {
            val intent = Intent(this, NotSignedActivity::class.java)
            startActivity(intent)
            finish()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
         }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onUserInfoLoaded(user: User) {
        drawer_layout.findViewById<TextView>(R.id.drawer_email).text = user.email
        drawer_layout.findViewById<TextView>(R.id.drawer_name).text = user.displayName()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

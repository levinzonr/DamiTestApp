package cz.levinzonr.damiapp.view.signed

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.google.android.gms.maps.MapFragment
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.model.local.DamiLocalDatasource
import cz.levinzonr.damiapp.presenter.MainPresenter
import cz.levinzonr.damiapp.view.contacts.ContactsListFragment
import cz.levinzonr.damiapp.view.map.MapsFragment
import cz.levinzonr.damiapp.view.unsigned.NotSignedActivity
import kotlinx.android.synthetic.main.activity_signed_in.*
import kotlinx.android.synthetic.main.app_bar_signed_in.*

class SignedInActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    private lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)
        setSupportActionBar(toolbar)
        presenter = MainPresenter()
        presenter.attachView(this)
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

    override fun onResume() {
        super.onResume()
        presenter.getUserInfo()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun replace(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.button_logout -> {
                AlertDialog.Builder(this)
                        .setTitle(R.string.action_logout)
                        .setMessage(R.string.action_logout_msg)
                        .setPositiveButton(android.R.string.yes,{_, _ -> presenter.logout()})
                        .setNegativeButton(android.R.string.no, {_, _ -> })
                        .create().show()
            }
            R.id.button_contacts -> {
                replace(ContactsListFragment())
            }
            R.id.button_map -> {
                replace(MapsFragment())
            }
         }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onUserInfoLoaded(user: User) {
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.drawer_email).text = user.email
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.drawer_name).text = user.displayName()
    }

    override fun onLogoutComplete() {
        startActivity(Intent(this, NotSignedActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

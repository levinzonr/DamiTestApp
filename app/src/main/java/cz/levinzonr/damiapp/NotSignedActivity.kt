package cz.levinzonr.damiapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.damiapp.view.LoginFragment
import cz.levinzonr.damiapp.view.MapsFragment
import kotlinx.android.synthetic.main.activity_not_signed.*

class NotSignedActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.view_container, LoginFragment())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_maps -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.view_container, MapsFragment())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_signed)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

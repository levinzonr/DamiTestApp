package cz.levinzonr.damiapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import cz.levinzonr.damiapp.view.session.login.LoginFragment
import cz.levinzonr.damiapp.view.MapsFragment
import cz.levinzonr.damiapp.view.session.BaseSignInFragment
import cz.levinzonr.damiapp.view.session.register.RegisterFragment
import kotlinx.android.synthetic.main.activity_not_signed.*
import kotlinx.android.synthetic.main.fragment_register.*

class NotSignedActivity : AppCompatActivity(), BaseSignInFragment.SignInInteractionListener {

    private lateinit var adapter: ViewPagerAdapter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                view_pager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_maps -> {
                view_pager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun registerMode(boolean: Boolean) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_signed)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initViewPager(view_pager)
    }

    private fun initViewPager(viewPager: ViewPager) {
        adapter = ViewPagerAdapter(supportFragmentManager, viewPager)
        adapter.addFragment(RegisterFragment())
        adapter.addFragment(MapsFragment())
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            private var prev = 0
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(prev).isChecked = false
                navigation.menu.getItem(position).isChecked = true
                prev = position
            }
        })
    }

    inner class ViewPagerAdapter(fm: FragmentManager, val view: ViewGroup) : FragmentPagerAdapter(fm) {

        private val fragments = ArrayList<Fragment>()

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }

}

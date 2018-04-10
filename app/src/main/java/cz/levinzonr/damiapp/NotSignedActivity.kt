package cz.levinzonr.damiapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.damiapp.view.login.LoginFragment
import cz.levinzonr.damiapp.view.MapsFragment
import kotlinx.android.synthetic.main.activity_not_signed.*

class NotSignedActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_signed)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initViewPager(view_pager)
    }

    private fun initViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment())
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

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragments = ArrayList<Fragment>()

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }

}

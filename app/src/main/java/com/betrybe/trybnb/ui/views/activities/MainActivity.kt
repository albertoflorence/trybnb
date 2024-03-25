package com.betrybe.trybnb.ui.views.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.betrybe.trybnb.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val mBottomNavigationMenu: BottomNavigationView by lazy { findViewById(R.id.navigation_bottom_view) }
    private val mReservationFragment = ReservationFragment()
    private val mCreateReservationFragment = CreateReservationFragment()
    private val mProfileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavigationMenu.setOnItemSelectedListener { item -> handleBottomNavigation(item) }
    }

    private fun setMainFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun handleBottomNavigation(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reservation_menu_item -> {
                setMainFragment(mReservationFragment)
                return true
            }

            R.id.create_reservation_menu_item -> {
                setMainFragment(mCreateReservationFragment)
                return true
            }

            R.id.profile_menu_tem -> {
                setMainFragment(mProfileFragment)
                return true
            }

            else -> return false
        }
    }
}

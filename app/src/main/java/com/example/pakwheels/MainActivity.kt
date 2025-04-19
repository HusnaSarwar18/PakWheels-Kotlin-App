package com.example.pakwheels

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("WrongConstant", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_myads -> {
                    replaceFragment(AllAdsFragment())
                    true
                }
                R.id.bottom_chat -> {
                    replaceFragment(MyAdsFragment())
                    true
                }
                R.id.bottom_menu -> {
                    replaceFragment(MenuFragment())
                    true
                }
                else -> false
            }
        }

        // Floating action button click to navigate to SellNowFragment
        val fabSell: FloatingActionButton = findViewById(R.id.bottom_fab)
        fabSell.setOnClickListener {
            replaceFragment(SellNowFragment())
        }

        // Check intent to navigate directly to HomeFragment if requested
        if (intent.getStringExtra("navigateTo") == "HomeFragment") {
            replaceFragment(HomeFragment())
        } else {
            // Default fragment load
            replaceFragment(HomeFragment())
        }
    }

    // Function to replace fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}

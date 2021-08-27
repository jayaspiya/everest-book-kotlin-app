package com.jayaspiya.everestbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.jayaspiya.everestbooks.fragments.DiscoverFragment
import com.jayaspiya.everestbooks.fragments.HomeFragment
import com.jayaspiya.everestbooks.fragments.MoreFragment

class HomeActivity : AppCompatActivity() {
    // Navigation Buttons
    private lateinit var navHome: Button
    private lateinit var navMore: Button
    private lateinit var navDiscover: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Navigation
        navHome = findViewById(R.id.navHome)
        navMore = findViewById(R.id.navMore)
        navDiscover = findViewById(R.id.navDiscover)

        navHome.setOnClickListener {
            startFragment(HomeFragment())
        }
        navMore.setOnClickListener {
            startFragment(MoreFragment())
        }

        navDiscover.setOnClickListener {
            startFragment(DiscoverFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        startFragment(HomeFragment())
    }
    private fun startFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
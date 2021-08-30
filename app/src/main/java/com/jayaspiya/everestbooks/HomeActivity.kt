package com.jayaspiya.everestbooks

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
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
        if(!checkUserPermission()) {
            askRunTimePermission()
        }
    }
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    private fun checkUserPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun askRunTimePermission() {
        ActivityCompat.requestPermissions(this, permissions, 1)
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
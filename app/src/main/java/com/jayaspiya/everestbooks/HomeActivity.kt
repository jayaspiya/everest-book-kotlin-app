package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.fragments.HomeFragment
import com.jayaspiya.everestbooks.fragments.MoreFragment
import com.jayaspiya.everestbooks.model.Book

class HomeActivity : AppCompatActivity() {
    // Navigation Buttons
    private lateinit var navHome: Button
    private lateinit var navMore: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Navigation
        navHome = findViewById(R.id.navHome)
        navMore = findViewById(R.id.navMore)

        navHome.setOnClickListener {
            startFragment(HomeFragment())
        }
        navMore.setOnClickListener {
            startFragment(MoreFragment())
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
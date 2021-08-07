package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.fragments.HomeFragment
import com.jayaspiya.everestbooks.fragments.MoreFragment
import com.jayaspiya.everestbooks.model.Book

class HomeActivity : AppCompatActivity() {
//    private lateinit var lvBooks: ListView
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
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.fragmentContainer, HomeFragment())
                addToBackStack(null)
                commit()
            }
        }
        navMore.setOnClickListener {
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.fragmentContainer, MoreFragment())
                addToBackStack(null)
                commit()
            }
        }





    }
}
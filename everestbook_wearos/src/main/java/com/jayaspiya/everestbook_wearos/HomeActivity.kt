package com.jayaspiya.everestbook_wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jayaspiya.everestbook_wearos.api.ServiceBuilder
import com.jayaspiya.everestbook_wearos.api.User
import com.jayaspiya.everestbook_wearos.api.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : Activity() {
    private lateinit var tvUsername: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val token = intent.getStringExtra("token").toString()
        tvUsername = findViewById(R.id.tvUsername)
        tvUsername.text = token
//        getData(token)
    }

    private fun getData(token: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getProfile(token)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val user = response.data!!
                        println(user)
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
}
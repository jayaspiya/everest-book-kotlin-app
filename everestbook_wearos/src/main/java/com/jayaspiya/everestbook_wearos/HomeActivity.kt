package com.jayaspiya.everestbook_wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jayaspiya.everestbook_wearos.api.ServiceBuilder
import com.jayaspiya.everestbook_wearos.api.User
import com.jayaspiya.everestbook_wearos.api.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : Activity() {
    private lateinit var tvUsername: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivProfilePicture: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val token = intent.getStringExtra("token").toString()
        tvUsername = findViewById(R.id.tvUsername)
        tvLocation = findViewById(R.id.tvLocation)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        getData(token)
    }

    private fun getData(token: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getProfile(token)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val user = response.data!!
                        tvUsername.text = "Hello "+ user.firstname!!.capitalize() + " "+ user.lastname!!.capitalize()
                        tvLocation.text = "Location " + user.address!!.capitalize()
                        tvPhone.text = "Phone " + user.phone
                        tvEmail.text = user.email
                        Glide.with(this@HomeActivity)
                            .load(user.profile)
                            .into(ivProfilePicture)
                        println(user)
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
}
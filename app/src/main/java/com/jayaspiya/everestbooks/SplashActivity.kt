package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jayaspiya.everestbooks.adapter.CartAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CoroutineScope(Dispatchers.Main).launch{
            delay(2000)
            getUserDetail()
            login()
            finish()
        }
    }

    private fun login() {
        if(ServiceBuilder.token == ""){
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        } else {
            getCart()
        }
    }

    private fun getCart() {
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getProfile()
                if(response.success == true){
                    ServiceBuilder.user = response.data!!
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                }
                else{
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                }
            }
        }
        catch(ex: Exception){
            println(ex)
        }
    }

    private fun getUserDetail() {
        val sharedPreferences = getSharedPreferences("userAuth", MODE_PRIVATE)
        ServiceBuilder.token = sharedPreferences.getString("token", "").toString()
    }
}
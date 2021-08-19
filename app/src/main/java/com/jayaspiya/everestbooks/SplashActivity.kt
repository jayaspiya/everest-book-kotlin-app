package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.database.EverestDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    var email: String? = ""
    var password: String? = ""
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
        CoroutineScope(Dispatchers.IO).launch{
            val user = EverestDB.getInstance(this@SplashActivity)
                .getUserDAO()
                .loginUser(email!!, password!!)

            if(user == null){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            else{
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            }
        }
    }

    private fun getUserDetail() {
        val sharedPreferences = getSharedPreferences("userAuth", MODE_PRIVATE)
        email = sharedPreferences.getString("email", "")
        password = sharedPreferences.getString("password", "")
    }
}
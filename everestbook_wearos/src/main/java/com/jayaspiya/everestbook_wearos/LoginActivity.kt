package com.jayaspiya.everestbook_wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jayaspiya.everestbook_wearos.api.ServiceBuilder
import com.jayaspiya.everestbook_wearos.api.User
import com.jayaspiya.everestbook_wearos.api.UserRepository
import com.jayaspiya.everestbook_wearos.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : Activity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.etEmail.text) -> {
                    binding.etEmail.requestFocus()
                    binding.etEmail.error = "Email can not be empty."
                }
                TextUtils.isEmpty(binding.etPassword.text) -> {
                    binding.etPassword.requestFocus()
                    binding.etPassword.error = "Password can not be empty."

                }
                else -> {
                    login()
                }
            }

        }
    }

    private fun login() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val email: String = binding.etEmail.text.toString()
                val password: String = binding.etPassword.text.toString()
                val user = User(email = email, password = password)
                val userRepo = UserRepository()
                val response = userRepo.loginUser(user)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.accessToken!!
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("token", response.accessToken!!)
                        startActivity(intent)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        print("abc")
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
}
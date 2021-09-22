package com.jayaspiya.everestbook_wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jayaspiya.everestbook_wearos.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : Activity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.etEmail.text) -> {
                    binding.apply {
                        etEmail.requestFocus()
                        etEmail.error = "Email can not be empty."
                    }
                }
                TextUtils.isEmpty(binding.etPassword.text) -> {
                    binding.apply {
                        etPassword.requestFocus()
                        etPassword.error = "Password can not be empty."
                    }
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
                    saveUserDetail()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
}
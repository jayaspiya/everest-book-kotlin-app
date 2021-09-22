package com.jayaspiya.everestbook_wearos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jayaspiya.everestbook_wearos.databinding.ActivityLoginBinding

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

    }
}
package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var tvSignup: TextView
    private lateinit var btnLogin: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvSignup = findViewById(R.id.tvSignup)
        btnLogin = findViewById(R.id.btnLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)


        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(etUsername.text)) {
                etUsername.requestFocus()
                etUsername.error = "Username can not be empty."
            } else if (TextUtils.isEmpty(etPassword.text)) {
                etPassword.requestFocus()
                etPassword.error = "Username can not be empty."
            }
            else{
                Toast.makeText(this@LoginActivity, "Login: ${etUsername.text}", Toast.LENGTH_LONG).show()
            }

        }
    }
}
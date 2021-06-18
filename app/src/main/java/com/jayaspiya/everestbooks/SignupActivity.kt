package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignupActivity : AppCompatActivity() {
    private lateinit var btnSignup:Button
    private lateinit var tvLogin: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btnSignup = findViewById(R.id.btnSignup)
        tvLogin = findViewById(R.id.tvLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)

        btnSignup.setOnClickListener {
            if (TextUtils.isEmpty(etUsername.text)) {
                etUsername.requestFocus()
                etUsername.error = "Username can not be empty."
            } else if (TextUtils.isEmpty(etPassword.text)) {
                etPassword.requestFocus()
                etPassword.error = "Username can not be empty."
            }
            else{
                Toast.makeText(this@SignupActivity, "Signup: ${etUsername.text}", Toast.LENGTH_LONG).show()
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) }
    }
}
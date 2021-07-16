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
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvSignup = findViewById(R.id.tvSignup)
        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)


        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(etEmail.text)) {
                etEmail.requestFocus()
                etEmail.error = "Email can not be empty."
            } else if (TextUtils.isEmpty(etPassword.text)) {
                etPassword.requestFocus()
                etPassword.error = "Password can not be empty."
            }
            else if(checkCredential()){
                startActivity(
                    Intent(this, HomeActivity::class.java)
                )
                finish()
            }
            else{
                Toast.makeText(this@LoginActivity, "Invalid Credential", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun checkCredential(): Boolean {
        val username: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()
        if(username == "admin" && password == "admin"){
            return true
        }
        return false
    }
}
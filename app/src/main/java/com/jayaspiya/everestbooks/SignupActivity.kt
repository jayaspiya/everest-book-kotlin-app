package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jayaspiya.everestbooks.api.UserRepository
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etCPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btnSignup = findViewById(R.id.btnSignup)
        tvLogin = findViewById(R.id.tvLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etCPassword = findViewById(R.id.etCPassword)

        btnSignup.setOnClickListener {
            if (TextUtils.isEmpty(etEmail.text)) {
                etEmail.requestFocus()
                etEmail.error = "Email can not be empty."
            } else if (TextUtils.isEmpty(etPassword.text)) {
                etPassword.requestFocus()
                etPassword.error = "Password can not be empty."
            } else if (TextUtils.isEmpty(etCPassword.text)) {
                etCPassword.requestFocus()
                etCPassword.error = "Confirm Password can not be empty."
            } else if (etPassword.text.toString() != etCPassword.text.toString()) {
                etCPassword.requestFocus()
                etCPassword.error = "Password and Confirm Password does not match"
            } else {
                val user =
                    User(email = etEmail.text.toString(), password = etPassword.text.toString())
                // Room DB
//                CoroutineScope(Dispatchers.IO).launch {
//                    EverestDB.getInstance(this@SignupActivity)
//                        .getUserDAO().registerUser(user)
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(this@SignupActivity, "User created", Toast.LENGTH_LONG).show()
//                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
//                        finish()
//                    }
//                }

                // Retrofit
                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        val userRepo = UserRepository()
                        val response = userRepo.registerUser(user)
                        if (response.success == true) {
                            withContext(Main) {
                                Toast.makeText(
                                    this@SignupActivity,
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        this@SignupActivity,
                                        LoginActivity::class.java
                                    )
                                )
                            }
                        }
                    }
                } catch (ex: Exception) {
                    println(ex)
                }
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
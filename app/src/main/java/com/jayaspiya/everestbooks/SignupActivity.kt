package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jayaspiya.everestbooks.repository.UserRepository
import com.jayaspiya.everestbooks.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etCPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btnSignup = findViewById(R.id.btnSignup)
        tvLogin = findViewById(R.id.tvLogin)
        etFirstname = findViewById(R.id.etFirstname)
        etLastname = findViewById(R.id.etLastname)
        etAddress = findViewById(R.id.etAddress)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etCPassword = findViewById(R.id.etCPassword)

        btnSignup.setOnClickListener {
            when {
                TextUtils.isEmpty(etFirstname.text) -> {
                    etFirstname.requestFocus()
                    etFirstname.error = "First name can not be empty."
                }
                TextUtils.isEmpty(etLastname.text) -> {
                    etLastname.requestFocus()
                    etLastname.error = "Last name can not be empty."
                }
                TextUtils.isEmpty(etAddress.text) -> {
                    etAddress.requestFocus()
                    etAddress.error = "Address can not be empty."
                }
                TextUtils.isEmpty(etPhone.text) -> {
                    etPhone.requestFocus()
                    etPhone.error = "Phone Number can not be empty."
                }
                TextUtils.isEmpty(etEmail.text) -> {
                    etEmail.requestFocus()
                    etEmail.error = "Email can not be empty."
                }
                TextUtils.isEmpty(etPassword.text) -> {
                    etPassword.requestFocus()
                    etPassword.error = "Password can not be empty."
                }
                TextUtils.isEmpty(etCPassword.text) -> {
                    etCPassword.requestFocus()
                    etCPassword.error = "Confirm Password can not be empty."
                }
                etPassword.text.toString() != etCPassword.text.toString() -> {
                    etCPassword.requestFocus()
                    etCPassword.error = "Password and Confirm Password does not match"
                }
                else -> {
                    val user =
                        User(
                            email = etEmail.text.toString(), password = etPassword.text.toString(),
                            firstname = etFirstname.text.toString(), lastname = etLastname.text.toString(),
                            address = etAddress.text.toString(), phone = etPhone.text.toString()
                        )
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
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
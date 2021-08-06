package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            else{
                login()
            }

        }
    }

    private fun login() {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch{
            val user: User? = EverestDB.getInstance(this@LoginActivity)
                .getUserDAO().loginUser(email, password)
            if(user == null){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, "Invalid Credential", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                saveUserDetail()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun saveUserDetail() {
        val sharedPreferences = getSharedPreferences("userAuth", MODE_PRIVATE);
        val editor = sharedPreferences.edit()
        editor.putString("email", etEmail.text.toString())
        editor.putString("password", etPassword.text.toString())
        editor.apply()
    }
}
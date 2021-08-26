package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.api.UserRepository
import com.jayaspiya.everestbooks.model.User
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
            } else {
                login()
            }

        }
    }

    private fun login() {


//        CoroutineScope(Dispatchers.IO).launch{
//            val user: User? = EverestDB.getInstance(this@LoginActivity)
//                .getUserDAO().loginUser(email, password)
//            if(user == null){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(this@LoginActivity, "Invalid Credential", Toast.LENGTH_SHORT).show()
//                }
//            }
//            else{
//                saveUserDetail()
//                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                finish()
//            }
//        }
        // Retrofit
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val email: String = etEmail.text.toString()
                val password: String = etPassword.text.toString()
                val user = User(email = email, password = password)
                val userRepo = UserRepository()
                val response = userRepo.loginUser(user)
                if (response.success == true) {
                    ServiceBuilder.token = response.accessToken!!
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

    private fun saveUserDetail() {
        val sharedPreferences = getSharedPreferences("userAuth", MODE_PRIVATE);
        val editor = sharedPreferences.edit()
        editor.putString("email", etEmail.text.toString())
        editor.putString("password", etPassword.text.toString())
        editor.apply()
    }
}
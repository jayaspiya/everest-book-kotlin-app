package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.jayaspiya.everestbooks.model.UserPasswordChange
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etCPassword: EditText
    private lateinit var btnSubmit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etCPassword = findViewById(R.id.etCPassword)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            when {
                TextUtils.isEmpty(etOldPassword.text) -> {
                    etOldPassword.requestFocus()
                    etOldPassword.error = "Old Password can not be empty."
                }
                TextUtils.isEmpty(etNewPassword.text) -> {
                    etNewPassword.requestFocus()
                    etNewPassword.error = "New Password can not be empty."
                }
                TextUtils.isEmpty(etCPassword.text) -> {
                    etCPassword.requestFocus()
                    etCPassword.error = "Confirm Password can not be empty."
                }
                else -> {
                    val oldPassword = etOldPassword.text.toString()
                    val newPassword = etNewPassword.text.toString()
                    val confirmPassword = etCPassword.text.toString()
                    if (newPassword != confirmPassword) {
                        etCPassword.requestFocus()
                        etCPassword.error = "Confirm Password can doesnot match."
                    }
                    else{
                        try{
                            CoroutineScope(IO).launch {
                                val repository = UserRepository()
                                val userPasswordChange = UserPasswordChange(oldPassword=oldPassword, newPassword=newPassword)
                                val response = repository.passwordChange(userPasswordChange)
                                if(response.success == true){
                                    withContext(Main){
                                        Toast.makeText(this@ChangePasswordActivity, "Password Changed", Toast.LENGTH_SHORT).show()
                                        val sharedPreferences = getSharedPreferences("userAuth", AppCompatActivity.MODE_PRIVATE);
                                        val editor = sharedPreferences.edit()
                                        editor.putString("token", "")
                                        editor.apply()
                                        startActivity(Intent(this@ChangePasswordActivity, LoginActivity::class.java))
                                        finish()
                                    }
                                }
                            }
                        }catch (ex: Exception){
                            println(ex)
                        }
                    }
                }
            }
        }
    }
}
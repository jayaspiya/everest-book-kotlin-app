package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnUpdate: Button
    private lateinit var progressBar: LinearLayout

    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        etFirstname = findViewById(R.id.etFirstname)
        etLastname = findViewById(R.id.etLastname)
        etAddress = findViewById(R.id.etAddress)
        etPhone = findViewById(R.id.etPhone)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnUpdate = findViewById(R.id.btnUpdate)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        getUserDate()
        btnUpdate.setOnClickListener {
            updateUser()
            super.onBackPressed()
            finish()
        }
    }

    private fun getGender(): String {
//        TODO: Gender not found
        var selectedGender = ""
        if (rdoFemale.isSelected) {
            selectedGender = "female"
        } else if (rdoMale.isSelected) {
            selectedGender = "male"
        } else if (rdoOthers.isSelected) {
            selectedGender = "other"
        }
        return selectedGender
    }

    private fun updateUser() {
        // TODO: Validation Check
        try {
            gender = getGender()
            Toast.makeText(this, "gender $gender", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val user = User(
                    firstname = etFirstname.text.toString(),
                    lastname = etLastname.text.toString(),
                    address = etAddress.text.toString(),
                    phone = etPhone.text.toString(),
                    gender = gender
                )
                println(user)
                val response = userRepository.updateUser(user)
                if (response.success == true) {
                    withContext(Main){
                        Toast.makeText(this@UpdateProfileActivity, "Profile Updated", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }

    private fun getUserDate() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getProfile()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        val user = response.data!!
                        etFirstname.setText(user.firstname)
                        etLastname.setText(user.lastname)
                        etAddress.setText(user.address)
                        etPhone.setText(user.phone)
                        when (user.gender) {
                            "male" -> {
                                rdoMale.isSelected = true
                            }
                            "female" -> {
                                rdoFemale.isSelected = true
                            }
                            "other" -> {
                                rdoOthers.isSelected = true
                            }
                        }
                        println(user)
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
}
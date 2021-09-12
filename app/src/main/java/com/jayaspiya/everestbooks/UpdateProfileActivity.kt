package com.jayaspiya.everestbooks

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var rdbMale: RadioButton
    private lateinit var rdbFemale: RadioButton
    private lateinit var rdoOther: RadioButton
    private var gender: String = "male"

    private lateinit var btnUpdate: Button
    private lateinit var btnDatePicker: Button
    private lateinit var tvDOB: TextView
    private lateinit var progressBar: LinearLayout

    var selectDate: String =""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        etFirstname = findViewById(R.id.etFirstname)
        etLastname = findViewById(R.id.etLastname)
        etAddress = findViewById(R.id.etAddress)
        etPhone = findViewById(R.id.etPhone)
        rdbMale = findViewById(R.id.rdbMale)
        rdbFemale = findViewById(R.id.rdbFemale)
        rdoOther = findViewById(R.id.rdoOther)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        tvDOB = findViewById(R.id.tvDOB)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        getUserDate()
        btnUpdate.setOnClickListener {
//            gender = getGender()
//            Toast.makeText(this, "gender $gender", Toast.LENGTH_SHORT).show()
            updateUser()
            super.onBackPressed()
            finish()
        }
        btnDatePicker.setOnClickListener {
            selectDatePicker()
        }
        rdbMale.setOnClickListener {
            gender = "male"
        }
        rdbFemale . setOnClickListener {
            gender = "female"
        }
        rdoOther . setOnClickListener {
            gender = "other"
        }
    }

//    private fun getGender(): String {
////        TODO: Gender not found
//        var selectedGender = ""
//        if (rdoFemale.isSelected) {
//            selectedGender = "female"
//        } else if (rdoMale.isSelected) {
//            selectedGender = "male"
//        } else if (rdoOthers.isSelected) {
//            selectedGender = "other"
//        }
//        else{
//            selectedGender = "why"
//        }
//        return selectedGender
//    }
    private fun selectDatePicker() {
            var cal = Calendar.getInstance()
            var limitYear = cal.get(Calendar.YEAR)
            var limitMonth = cal.get(Calendar.MONTH)
            var limitDay = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    if (dayOfMonth <= limitDay || month < limitMonth || year < limitYear) {
                        selectDate = "${year}-${month+1}-${dayOfMonth}"
                        tvDOB.text = selectDate
                    } else {
                        Toast.makeText(this, "You have to pick a past date", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                limitYear,
                limitMonth,
                limitDay
            ).show()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUser() {
        // TODO: Validation Check
        try {
            Toast.makeText(this, "gender $gender", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val user = User(
                    firstname = etFirstname.text.toString(),
                    lastname = etLastname.text.toString(),
                    address = etAddress.text.toString(),
                    phone = etPhone.text.toString(),
                    gender = gender,
                    DOB = selectDate
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
                    withContext(Main) {
                        progressBar.visibility = View.GONE
                        val user = response.data!!
                        etFirstname.setText(user.firstname)
                        etLastname.setText(user.lastname)
                        etAddress.setText(user.address)
                        etPhone.setText(user.phone)
                        tvDOB.text = user.DOB.toString()
                        when (user.gender) {
                            "male" -> {
                                rdbMale.isChecked = true
                            }
                            "female" -> {
                                rdbFemale.isChecked = true
                            }
                            "other" -> {
                                rdoOther.isChecked = true
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
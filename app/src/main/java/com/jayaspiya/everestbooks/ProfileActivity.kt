package com.jayaspiya.everestbooks

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var ivProfilePicture: ImageView
    private lateinit var ibSetting: ImageButton
    private lateinit var tvUsername: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var progressBar: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        tvUsername = findViewById(R.id.tvUsername)
        tvAddress = findViewById(R.id.tvAddress)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        ibSetting = findViewById(R.id.ibSetting)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getProfile()
                if (response.success == true) {
                    withContext(Main) {
                        progressBar.visibility = View.GONE
                        val user = response.data!!
                        println(user)
                        tvUsername.text = user.firstname + " " + user.lastname
                        tvAddress.text = user.address
                        tvPhone.text = user.phone
                        tvEmail.text = user.email

                        Glide.with(this@ProfileActivity)
                            .load(user.profile)
                            .into(ivProfilePicture)
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
        ivProfilePicture.setOnClickListener {
            loadPopUpProfileUpload()
        }
        ibSetting.setOnClickListener {
            loadPopUpSetting()
        }
    }

    private fun loadPopUpSetting() {
        Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
        val popMenu = PopupMenu(this, ibSetting)
        popMenu.menuInflater.inflate(R.menu.profile_menu, popMenu.menu)
        popMenu.setOnMenuItemClickListener {
            Toast.makeText(this, it.itemId.toString() , Toast.LENGTH_SHORT).show()
            true
        }
        popMenu.show()
    }

    private fun loadPopUpProfileUpload() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        val popMenu = PopupMenu(this, ivProfilePicture)
        popMenu.menuInflater.inflate(R.menu.profile_picture_menu, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuGallery) {
                openGallery()
            } else if (item.itemId == R.id.menuCamera) {
                openCamera()
            }
            true
        }
        popMenu.show()
    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)
    }

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 0
    private var imageUrl = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //gallery
            if (requestCode == GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                ivProfilePicture.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                ivProfilePicture.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}
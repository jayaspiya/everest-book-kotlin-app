package com.jayaspiya.everestbooks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.adapter.RecentlyViewedAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.sqrt
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() , SensorEventListener {
    private var sensor: Sensor? = null
    private lateinit var currentSensorManager: SensorManager
    private var sensorManager: SensorManager? = null


    private lateinit var ivProfilePicture: ImageView
    private lateinit var iv_add_profile: ImageView
    private lateinit var ibSetting: ImageButton
    private lateinit var tvUsername: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvOrders: TextView
    private lateinit var tvReviews: TextView
    private lateinit var progressBar: LinearLayout
    private lateinit var rvRecentlyViewed: RecyclerView

    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        currentSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        iv_add_profile = findViewById(R.id.iv_add_profile)
        tvUsername = findViewById(R.id.tvUsername)
        tvAddress = findViewById(R.id.tvAddress)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        tvOrders = findViewById(R.id.tvOrders)
        tvReviews = findViewById(R.id.tvReviews)
        ibSetting = findViewById(R.id.ibSetting)
        progressBar = findViewById(R.id.progressBar)
        rvRecentlyViewed = findViewById(R.id.rvRecentlyViewed)
        getData()
        iv_add_profile.setOnClickListener {
            loadPopUpProfileUpload()
        }
        ibSetting.setOnClickListener {
            loadPopUpSetting()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH
        if (!checkSensor())
            return
        else {
            sensor = currentSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            currentSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (currentSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        if(values<=0){
            startActivity(Intent(this,OrderActivity::class.java))
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val currentX = event.values[0]
            val currentY = event.values[1]
            val currentZ = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((currentX * currentX + currentY * currentY + currentZ * currentZ).toDouble()).toFloat()
            val changeValue: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + changeValue
            if (acceleration > 12) {
                startActivity(Intent(this@ProfileActivity,MapsActivity::class.java))
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        getData()
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }
    private fun getData() {
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
                        tvUsername.text = user.firstname!!.capitalize() + " " + user.lastname!!.capitalize()
                        tvAddress.text = user.address
                        tvPhone.text = user.phone
                        tvEmail.text = user.email
                        tvReviews.text = "Total Reviews: " + user.reviews.toString()
                        tvOrders.text = "Total Orders: " + user.orders.toString()
                        Glide.with(this@ProfileActivity)
                            .load(user.profile)
                            .into(ivProfilePicture)
                        val adapter = user.recentlyViewed?.let { RecentlyViewedAdapter(it, this@ProfileActivity) }
                        rvRecentlyViewed.layoutManager = LinearLayoutManager(this@ProfileActivity, LinearLayoutManager.HORIZONTAL, true)
                        rvRecentlyViewed.adapter = adapter
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }

    private fun loadPopUpSetting() {
        val popMenu = PopupMenu(this, ibSetting)
        popMenu.menuInflater.inflate(R.menu.profile_menu, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuUpdateProfile) {
                startActivity(Intent(this,UpdateProfileActivity::class.java))
            } else if (item.itemId == R.id.menuChangePassword) {
                startActivity(Intent(this,ChangePasswordActivity::class.java))
            }
            true
        }
        popMenu.show()
    }

    private fun loadPopUpProfileUpload() {
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
            uploadImage()
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
    private fun uploadImage() {
        progressBar.visibility = View.VISIBLE
        val file = File(imageUrl)
        val mimeType = getMimeType(file)
        val reqFile = RequestBody.create(mimeType?.let { it.toMediaTypeOrNull() }, file)
        val body = MultipartBody.Part.createFormData("profile", file.name, reqFile)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.uploadImage(body)
                if(response.success == true){
                    withContext(Main){
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@ProfileActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (ex: Exception) {
                println(ex.localizedMessage)
            }
        }
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}
package com.jayaspiya.everestbooks.fragments

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.ProfileActivity
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.database.EverestDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        sensorManager = requireContext().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        val bookRecyclerView: RecyclerView = view.findViewById(R.id.bookRecyclerView)
        val progressBar: LinearLayout = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        try {
            val bookRepository = BookRepository(requireContext(), EverestDB.getInstance(requireContext()).getBookDAO())
            CoroutineScope(IO).launch {
                val bookList = bookRepository.getBooks()
                    withContext(Main){
                        progressBar.visibility = View.GONE
                        val adapter = BookAdapter(bookList!!, requireContext())
                        bookRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                        bookRecyclerView.adapter = adapter
                    }
                }
        }
        catch (ex: Exception){
            println(ex)
        }
        if (!checkSensor()) {
            return null
        }
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        return view
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
            flag = false
        }
        return flag
    }
    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
        if (values < 5)
        else if (values > 10){
            startActivity(Intent(requireContext(),ProfileActivity::class.java))

        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
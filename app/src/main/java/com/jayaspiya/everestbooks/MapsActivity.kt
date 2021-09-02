package com.jayaspiya.everestbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jayaspiya.everestbooks.databinding.ActivityMapsBinding
import com.jayaspiya.everestbooks.model.LatitudeLongitude

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val store1 = LatitudeLongitude(27.69486292102715, 84.4230918354373, "Everest Books, Milan road")
        val store2 = LatitudeLongitude(27.691965134338325,   84.42483839591884, "Everest Books, Lionschowk")
        val locations = arrayListOf<LatitudeLongitude>(store1, store2)

        for (location in locations) {
            mMap.addMarker(
                MarkerOptions().position(LatLng(location.latitude, location.longitude))
                    .title(location.markerName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(27.69486292102715, 84.4230918354373), 16F));
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}
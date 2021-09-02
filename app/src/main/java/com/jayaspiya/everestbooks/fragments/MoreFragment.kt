package com.jayaspiya.everestbooks.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jayaspiya.everestbooks.*

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        val logout: Button = view.findViewById(R.id.btnLogout)
        val btnCart: Button = view.findViewById(R.id.btnCart)
        val btnProfile: Button = view.findViewById(R.id.btnProfile)
        val btnMaps: Button = view.findViewById(R.id.btnMaps)
        logout.setOnClickListener {
            logoutUser()
        }
        btnCart.setOnClickListener {
            startActivity(Intent(requireContext(), CartActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        btnMaps.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        }
        return view
    }

    private fun logoutUser() {
        val sharedPreferences = requireContext().getSharedPreferences("userAuth", AppCompatActivity.MODE_PRIVATE);
        val editor = sharedPreferences.edit()
        editor.putString("token", null)
        editor.apply()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }


}
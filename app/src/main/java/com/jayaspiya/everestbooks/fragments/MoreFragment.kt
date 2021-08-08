package com.jayaspiya.everestbooks.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jayaspiya.everestbooks.LoginActivity
import com.jayaspiya.everestbooks.R

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        val logout: Button = view.findViewById(R.id.btnLogout)
        logout.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("userAuth", AppCompatActivity.MODE_PRIVATE);
            val editor = sharedPreferences.edit()
            editor.putString("email", "")
            editor.putString("password", "")
            editor.apply()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        return view
    }


}
package com.jayaspiya.everestbooks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.jayaspiya.everestbooks.R


class LibraryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        val btnCart: Button = view.findViewById(R.id.btnCart)
        btnCart.setOnClickListener {
            val fragment = parentFragmentManager.beginTransaction()
            fragment.replace(R.id.fragmentContainer, CartFragment())
            fragment.addToBackStack("library_fragment")
            fragment.commit()
        }
        return view
    }
}
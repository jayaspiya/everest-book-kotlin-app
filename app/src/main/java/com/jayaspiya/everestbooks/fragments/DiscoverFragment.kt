package com.jayaspiya.everestbooks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverFragment : Fragment() {
    private lateinit var svSearch: SearchView
    private lateinit var progressBar: LinearLayout
    private lateinit var bookRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_discover, container, false)

        svSearch= view.findViewById(R.id.svSearch)
        progressBar= view.findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        bookRecyclerView= view.findViewById(R.id.bookRecyclerView)
        svSearch.setOnQueryTextListener(
            object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    getBook(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
        return view

    }

    private fun getBook(pattern: String) {
        progressBar.visibility = View.VISIBLE
        try {
            val bookRepository = BookRepository()
            CoroutineScope(Dispatchers.IO).launch {
                val response = bookRepository.searchBook(getSlug(pattern))
                withContext(Dispatchers.Main){
                    progressBar.visibility = View.GONE
                    val adapter = BookAdapter(response.data!!, requireContext())
                    bookRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                    bookRecyclerView.adapter = adapter
                }
            }
        }
        catch (ex: Exception){
            println(ex)
        }
    }

    private fun getSlug(pattern: String): String {
        val parts = pattern.split(" ")
        return parts.joinToString("-")
    }
}
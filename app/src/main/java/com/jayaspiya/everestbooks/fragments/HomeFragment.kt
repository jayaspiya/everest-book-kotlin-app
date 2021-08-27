package com.jayaspiya.everestbooks.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.api.BookRepository
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val bookRecyclerView: RecyclerView = view.findViewById(R.id.bookRecyclerView)
        var bookList = ArrayList<Book>()
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository()
                val response = bookRepository.getBooks()
                Log.d("data", response.toString())
                if(response.success == true){
                    bookList = response.data!!
                    println("************************************************************")
                    println("************************************************************")
                    println("************************************************************")
                    for(book in bookList){
                        println(book)
                    }
//                    EverestDB.getInstance(requireContext())
//                        .getBookDAO().addBook()
                    withContext(Main){
                        val adapter = BookAdapter(bookList, requireContext())
                        bookRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                        bookRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                        bookRecyclerView.adapter = adapter
                        return@withContext view
                    }
                }
            }
        }
        catch (ex: Exception){
            println(ex)
        }
        return view
    }


}
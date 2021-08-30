package com.jayaspiya.everestbooks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.entity.BookEntity
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
        val progressBar: LinearLayout = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        var bookList = ArrayList<Book>()
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository(requireContext())
                val response = bookRepository.getBooks()
                if(response.success == true){
                    bookList = response.data!!
                    // Save book to Room DB
//                    bookRepository.delBookFromDB()

                    for(book in bookList){
                        val bookEn = BookEntity(
                            _id = book._id,
                            title = book.title,
                            author = book.author,
                            isbn = book.isbn,
                            synopsis = book.synopsis,
                            price = book.price,
                            cover = book.cover?.front
                        )
                        bookRepository.addBookFromDB(bookEn)
                    }
//                    val bookList2 = bookRepository.getBookFromDB()
                    withContext(Main){
                        progressBar.visibility = View.GONE
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
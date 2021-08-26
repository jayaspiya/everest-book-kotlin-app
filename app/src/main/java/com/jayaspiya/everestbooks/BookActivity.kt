package com.jayaspiya.everestbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.api.BookRepository
import com.jayaspiya.everestbooks.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class BookActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvBookPrice: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivBook: ImageView
    private lateinit var btnAddToCart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        tvTitle = findViewById(R.id.tvTitle)
        tvBookPrice = findViewById(R.id.tvBookPrice)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        ivBook = findViewById(R.id.ivBook)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        val id = intent.getStringExtra("id")
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository()
                val response = bo
            }
        }
        catch (ex: Exception){
            println(ex)
        }
//        val book: Book? = intent.getParcelableExtra("book")
//
//        if (book != null) {
//            tvTitle.text = book.title.toString()
//            tvBookPrice.text = "Rs." + book.price.toString()
//            tvAuthor.text = book.author.toString()
//            tvDescription.text = book.synopsis.toString()
//            Glide.with(this)
//                .load(book.imageUrl.toString())
//                .into(ivBook)
//        }

    }
}
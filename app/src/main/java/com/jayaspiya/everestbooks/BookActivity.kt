package com.jayaspiya.everestbooks

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jayaspiya.everestbooks.api.BookRepository
import com.jayaspiya.everestbooks.fragments.CartFragment
import com.jayaspiya.everestbooks.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class BookActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvBookPrice: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivBook: ImageView
    private lateinit var btnAddToCart: Button
    private lateinit var myLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        tvTitle = findViewById(R.id.tvTitle)
        tvBookPrice = findViewById(R.id.tvBookPrice)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        ivBook = findViewById(R.id.ivBook)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        myLayout = findViewById(R.id.myLayout)
        btnAddToCart.setOnClickListener {
            val snackBar= Snackbar.make(myLayout,"Book Added to Cart", Snackbar.LENGTH_SHORT).show()
//            snackBar.setAction("View Cart"){
//                startActivity(Intent(this@BookActivity,HomeActivity::class.java))
//            }.show()
        }
        val id: String? = intent.getStringExtra("id")
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository()
                val response = bookRepository.getBook(id!!)
                if(response.success == true){
                    withContext(Main){
                        val book = response.data?.get(0)!!
                        tvTitle.text = book.title
                        tvBookPrice.text = "Rs." + book.price.toString()
                        tvAuthor.text = book.author
                        tvDescription.text = book.synopsis
                        Glide.with(this@BookActivity)
                            .load(book.cover?.front.toString())
                            .into(ivBook)
                    }
                }
                else{

                }
            }
        }
        catch (ex: Exception){
            println(ex)
        }

    }
}
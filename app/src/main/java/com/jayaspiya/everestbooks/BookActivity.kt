package com.jayaspiya.everestbooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.repository.UserRepository
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
    private lateinit var id: String
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
            addToCart()

        }
        val id: String? = intent.getStringExtra("id")
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository(this@BookActivity)
                val response = bookRepository.getBook(id!!)
                if(response.success == true){
                    withContext(Main){
                        val book = response.data?.get(0)!!
                        this@BookActivity.id = book._id
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
                    withContext(Main){
                        Toast.makeText(this@BookActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        catch (ex: Exception){
            println(ex)
        }

    }

    private fun addToCart() {
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.addToCart(id)
                if(response.success == true){
                    val snackBar= Snackbar.make(myLayout,"Book Added to Cart", Snackbar.LENGTH_SHORT)
                    snackBar.setAction("View Cart"){
                        startActivity(Intent(this@BookActivity,CartActivity::class.java))
                    }.show()
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@BookActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        catch(ex: Exception){
            println(ex)
        }
    }
}
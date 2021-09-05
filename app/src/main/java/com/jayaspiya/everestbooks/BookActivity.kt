package com.jayaspiya.everestbooks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.hdd.globalmovie.adapter.BookCoverAdapter
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvBookPrice: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnAddToCart: Button
    private lateinit var myLayout: LinearLayout
    private lateinit var bookCoverViewPager: ViewPager
    private lateinit var bookCoverTabLayout: TabLayout
    private lateinit var progressBar: LinearLayout

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        tvTitle = findViewById(R.id.tvTitle)
        tvBookPrice = findViewById(R.id.tvBookPrice)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        myLayout = findViewById(R.id.myLayout)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        // View Pager
        bookCoverViewPager = findViewById(R.id.bookCoverViewPager)
        bookCoverTabLayout = findViewById(R.id.bookCoverTabLayout)

        // Button Click Listener
        btnAddToCart.setOnClickListener {
            addToCart()
        }

        val id: String? = intent.getStringExtra("id")
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository(this@BookActivity)
                val response = bookRepository.getBook(id!!)
                if (response.success == true) {
                    withContext(Main) {
                        progressBar.visibility = View.GONE
                        val book = response.data?.get(0)!!
                        this@BookActivity.id = book._id
                        tvTitle.text = book.title
                        tvBookPrice.text = "Rs." + book.price.toString()
                        tvAuthor.text = book.author
                        tvDescription.text = book.synopsis
                        val coverImage = book.cover!!
                        var cover = arrayListOf<String>(coverImage.front.toString(),coverImage.back.toString())
                        bookCoverViewPager.adapter = BookCoverAdapter(cover)
                        bookCoverTabLayout.setupWithViewPager(bookCoverViewPager)
                    }
                } else {
                    withContext(Main) {
                        Toast.makeText(this@BookActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }

    }

    private fun addToCart() {
//        TODO: Token Malformed Beaerer Null
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.addToCart(id)
                if (response.success == true) {
                    val snackBar =
                        Snackbar.make(myLayout, "Book Added to Cart", Snackbar.LENGTH_SHORT)
                    snackBar.setAction("View Cart") {
                        startActivity(Intent(this@BookActivity, CartActivity::class.java))
                    }.show()
                } else {
                    withContext(Main) {
                        Toast.makeText(this@BookActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }

}

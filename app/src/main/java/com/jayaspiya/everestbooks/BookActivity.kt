package com.jayaspiya.everestbooks

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.hdd.globalmovie.adapter.BookCoverAdapter
import com.jayaspiya.everestbooks.adapter.ReviewAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
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
    private lateinit var btnAddToCart: FloatingActionButton
    private lateinit var myLayout: LinearLayout
    private lateinit var snackDesign: RelativeLayout
    private lateinit var bookCoverViewPager: ViewPager
    private lateinit var bookCoverTabLayout: TabLayout
    private lateinit var progressBar: LinearLayout
    private lateinit var rvReview: RecyclerView

    private lateinit var id: String
    private var inCart: Boolean = true

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        tvTitle = findViewById(R.id.tvTitle)
        tvBookPrice = findViewById(R.id.tvBookPrice)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        myLayout = findViewById(R.id.myLayout)
        snackDesign = findViewById(R.id.snackDesign)
        rvReview = findViewById(R.id.rvReview)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        myLayout.visibility = View.GONE

        // View Pager
        bookCoverViewPager = findViewById(R.id.bookCoverViewPager)
        bookCoverTabLayout = findViewById(R.id.bookCoverTabLayout)

        id = intent.getStringExtra("id").toString()

        // Button Click Listener
        btnAddToCart.setOnClickListener {
            if (inCart) {
                val snackBar =
                    Snackbar.make(myLayout, "Book Already On Cart", Snackbar.LENGTH_SHORT)
                snackBar.setAction("View Cart") {
                    startActivity(Intent(this@BookActivity, CartActivity::class.java))
                }.show()
            } else {
                addToCart()
                inCart = true
                val snackbar = Snackbar.make(snackDesign,"Item Added",Snackbar.LENGTH_SHORT)
                snackbar.setAction("View Cart"){
                    startActivity(Intent(this, CartActivity::class.java ))
                }.show()
                btnAddToCart.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.prime))
            }
        }

        getBook()

    }

    private fun addToCart() {
        CoroutineScope(IO).launch {
            try {
                val userRepository=UserRepository()
                val response = userRepository.addToCart(id)
                if (response.success==true) {
                    Log.i("MY LOG", response.message!!)
                }
            }catch (ex:Exception){
                print(ex)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBook() {
        try {
            CoroutineScope(IO).launch {
                val bookRepository = BookRepository(this@BookActivity)
                val response = bookRepository.getBook(id!!)
                if (response.success == true) {
                    withContext(Main) {
                        progressBar.visibility = View.GONE
                        myLayout.visibility = View.VISIBLE
                        val book = response.data?.get(0)!!
                        this@BookActivity.id = book._id
                        tvTitle.text = capitalizeSentence(book.title!!)
                        tvBookPrice.text = "Rs." + book.price.toString()
                        tvAuthor.text = capitalizeSentence(book.author!!)
                        tvDescription.text = book.synopsis
                        val coverImage = book.cover!!
                        var cover = arrayListOf<String>(
                            coverImage.front.toString(),
                            coverImage.back.toString()
                        )
                        // Check if Book is in User Cart
                        if (book.inCart==true) {
                            inCart = true
                            btnAddToCart.imageTintList =
                                ColorStateList.valueOf(resources.getColor(R.color.prime))
                        } else {
                            inCart = false
                            btnAddToCart.imageTintList =
                                ColorStateList.valueOf(resources.getColor(R.color.bg_graydark))
                        }
                        bookCoverViewPager.adapter = BookCoverAdapter(cover)
                        bookCoverTabLayout.setupWithViewPager(bookCoverViewPager)
                        val adapter = ReviewAdapter(book.reviews, this@BookActivity)
                        // Stop Scrolling Recycler View
                        val myLinearLayoutManager =
                            object : LinearLayoutManager(this@BookActivity) {
                                override fun canScrollVertically(): Boolean {
                                    return false
                                }
                            }
                        rvReview.layoutManager = myLinearLayoutManager
                        rvReview.adapter = adapter
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



    fun capitalizeSentence(sentence: String): String {
        val words = sentence.split(" ")?.toMutableList()
        var output = ""
        for (word in words) {
            output += word.capitalize() + " "
        }
        return output.trim()
    }

}



package com.jayaspiya.everestbooks

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
import com.jayaspiya.everestbooks.model.Book
import com.jayaspiya.everestbooks.model.Review
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.repository.ReviewRepository
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
    private lateinit var btnCreateReview: Button

    private lateinit var id: String
    private var inCart: Boolean = true
    private lateinit var reviewList: MutableList<Review>

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
        btnCreateReview = findViewById(R.id.btnCreateReview)
        progressBar = findViewById(R.id.progressBar)


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
                btnAddToCart.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.prime))
            }
        }
        btnCreateReview.setOnClickListener {
            val reviewDialog=Dialog(this)
            reviewDialog.setContentView(R.layout.review_dialog)
            reviewDialog.setCancelable(true)
            reviewDialog.setCanceledOnTouchOutside(true)
            reviewDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            val etReview: EditText =reviewDialog.findViewById(R.id.etReview)
            val spinnerRating: Spinner =reviewDialog.findViewById(R.id.spinnerRating)
            val btnSubmit: Button =reviewDialog.findViewById(R.id.btnSubmit)
            val adapter = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, arrayOf(1,2,3,4,5)
            )
            spinnerRating.adapter = adapter
            reviewDialog.show()

            btnSubmit.setOnClickListener{
                if(TextUtils.isEmpty(etReview.text)){
                    etReview.requestFocus()
                    etReview.error = "Review name can not be empty."
                }
                else{
                    val review = etReview.text.toString()
                    val rating = spinnerRating.selectedItem.toString().toInt()
                    addReview(review, rating)
                    reviewDialog.hide()
                    reviewList.add(0, Review(
                        rating = rating,
                        description = review,
                        user = ServiceBuilder.user
                    ))
                    setReviewRecyclerView(reviewList)
                }
            }
        }

        getBook()

    }

    private fun addReview(description: String, rating: Int) {
        CoroutineScope(IO).launch {
            try {
                val repository = ReviewRepository()
                val review = Review(rating = rating, description = description)
                repository.addReview(id,review)
            }
            catch(ex: Exception){
                println(ex)
            }
        }
    }

    private fun addToCart() {
        CoroutineScope(IO).launch {
            try {
                val userRepository=UserRepository()
                userRepository.addToCart(id)
            }catch (ex:Exception){
                print(ex)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBook() {
        progressBar.visibility = View.VISIBLE
        myLayout.visibility = View.GONE
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
                        reviewList = book.reviews!!
                        setReviewRecyclerView(reviewList)
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

    private fun setReviewRecyclerView(reviews:MutableList<Review>) {
        val adapter = ReviewAdapter(reviews, this@BookActivity)
        // Stop Scrolling Recycler View
        val myLinearLayoutManager =
            object : LinearLayoutManager(this@BookActivity) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        rvReview.layoutManager = myLinearLayoutManager
        rvReview.adapter = adapter
        adapter.notifyItemInserted(0)
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



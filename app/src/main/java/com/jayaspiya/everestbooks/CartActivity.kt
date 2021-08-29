package com.jayaspiya.everestbooks

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.adapter.BookAdapter
import com.jayaspiya.everestbooks.adapter.CartAdapter
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var progressBar: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        bookRecyclerView = findViewById(R.id.bookRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getCart()
                if(response.success == true){
                    val bookList = response.data!!
                    withContext(Main){
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@CartActivity, response.message, Toast.LENGTH_SHORT).show()
                        val adapter = CartAdapter(bookList, this@CartActivity)
                        bookRecyclerView.layoutManager = LinearLayoutManager(
                            this@CartActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        bookRecyclerView.adapter = adapter
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@CartActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        catch(ex: Exception){
            println(ex)
        }

    }
}
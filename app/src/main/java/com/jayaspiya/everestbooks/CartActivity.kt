package com.jayaspiya.everestbooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.adapter.CartAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.repository.OrderRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var progressBar: LinearLayout
    private lateinit var ivEmpty: ImageView
    private lateinit var tvEmpty: TextView
    private lateinit var btnOrder: Button
    private var message: String = ""
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        bookRecyclerView = findViewById(R.id.bookRecyclerView)
        ivEmpty = findViewById(R.id.ivEmpty)
        tvEmpty = findViewById(R.id.tvEmpty)
        btnOrder = findViewById(R.id.btnOrder)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        btnOrder.visibility = View.GONE
        getCart()
        btnOrder.setOnClickListener {
            placeOrder()
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun placeOrder() {
        CoroutineScope(IO).launch {
            try {
                val repository = OrderRepository()
                repository.placeOrder(ServiceBuilder.orderBook)
//            message = response.message!!
            }
            catch(ex: Exception){
                println(ex)
            }
        }
    }


    private fun getCart() {
        try {
            CoroutineScope(IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.getCart()
                if(response.success == true){
                    val bookList = response.data!!
                    withContext(Main){
                        if(bookList.isNotEmpty()){
                            ivEmpty.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                            btnOrder.visibility = View.VISIBLE
                            val newOrderBook:MutableList<OrderItem> = ArrayList()
                            for (book in bookList){
                                newOrderBook.add(OrderItem(book._id, 1, book.price))
                            }
                            ServiceBuilder.orderBook.orderBook = newOrderBook
                        }
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@CartActivity, response.message, Toast.LENGTH_SHORT).show()
                        val adapter = CartAdapter(bookList, this@CartActivity)
                        bookRecyclerView.layoutManager = LinearLayoutManager(
                            this@CartActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        bookRecyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
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

/// checkout button clicked--> order = 4
//
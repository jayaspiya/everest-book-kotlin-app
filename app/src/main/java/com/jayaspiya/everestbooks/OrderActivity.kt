package com.jayaspiya.everestbooks

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayaspiya.everestbooks.adapter.CartAdapter
import com.jayaspiya.everestbooks.adapter.OrderBookAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.OrderBook
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.repository.OrderRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderActivity : AppCompatActivity() {
    private lateinit var progressBar: LinearLayout
    private lateinit var rvOrderList: RecyclerView
    private lateinit var ivEmpty: ImageView
    private lateinit var tvEmpty: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        progressBar = findViewById(R.id.progressBar)
        ivEmpty = findViewById(R.id.ivEmpty)
        tvEmpty = findViewById(R.id.tvEmpty)
        rvOrderList = findViewById(R.id.rvOrderList)
        progressBar.visibility = View.VISIBLE
        getOrder()
    }
    private fun getOrder() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val orderRepository = OrderRepository()
                val response = orderRepository.getOrder()
                if(response.success == true){
                    var orderBook = response.data!!
                    withContext(Dispatchers.Main){
                        progressBar.visibility = View.GONE
                        if(orderBook.isNotEmpty()){
                            ivEmpty.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                        }
                        val adapter = OrderBookAdapter(orderBook, this@OrderActivity)
                        rvOrderList.layoutManager = LinearLayoutManager(this@OrderActivity)
                        rvOrderList.adapter = adapter
                    }
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@OrderActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        catch(ex: Exception){
            println(ex)
        }
    }
}
package com.jayaspiya.everestbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jayaspiya.everestbooks.adapter.CartAdapter
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.repository.OrderRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderActivity : AppCompatActivity() {
    private lateinit var progressBar: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        progressBar = findViewById(R.id.progressBar)
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
                        if(orderBook.isNotEmpty()){
                            progressBar.visibility = View.GONE
                        }
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
package com.jayaspiya.everestbooks

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    private val channelID = "com.jayaspiya.everestbooks"
    private var notificationManager: NotificationManager? = null
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
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "Everest Book", "Notification Channel")
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
            startActivity(Intent(this, HomeActivity::class.java))
            displayNotification()
        }
    }

    private fun placeOrder() {
        CoroutineScope(IO).launch {
            try {
                val repository = OrderRepository()
                repository.placeOrder(ServiceBuilder.orderBook)
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
                                newOrderBook.add(OrderItem(book, 1, book.price))
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
    @SuppressLint("RestrictedApi")
    private fun displayNotification() {
        val notificationId = 66

        val intent = Intent(this, OrderActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action : NotificationCompat.Action =
            NotificationCompat.Action.Builder(0,"View Order",pendingIntent).build()

        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Everest Book")
            .setContentText("Your order has been confirmed.")
            .setSmallIcon(R.drawable.mail)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(action)
            .build()
        CoroutineScope(IO).launch {
            delay(3000)
            withContext(Main){
                notificationManager?.notify(notificationId, notification)
            }
        }
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }

    }
}
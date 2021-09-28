package com.jayaspiya.everestbooks.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.BookActivity
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.model.Book
import com.jayaspiya.everestbooks.model.OrderBook

class OrderBookAdapter(
    private val orderList: MutableList<OrderBook>,
    val context: Context
) : RecyclerView.Adapter<OrderBookAdapter.OrderBookViewHolder>() {
    class OrderBookViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvOrder: TextView = view.findViewById(R.id.tvOrder)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_book_recycler_view, parent, false)
        return OrderBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderBookViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvOrder.text = "Order ID: " + order._id
        holder.tvStatus.text = "Status: " + order.status
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
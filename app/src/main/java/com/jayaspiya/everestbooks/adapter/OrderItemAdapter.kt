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
import com.jayaspiya.everestbooks.model.OrderItem

class OrderItemAdapter(
    private val orderList: MutableList<OrderItem>,
    val context: Context
) : RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder>() {
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvBook: TextView = view.findViewById(R.id.tvBook)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_recycler_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvBook.text = order.bookId!!.title
        holder.tvQuantity.text =  order.qty.toString()
        holder.tvPrice.text =  "Rs. " + order.price.toString()
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
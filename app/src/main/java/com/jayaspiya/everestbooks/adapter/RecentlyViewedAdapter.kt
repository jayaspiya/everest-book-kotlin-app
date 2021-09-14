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

class RecentlyViewedAdapter(
    private val bookList: MutableList<Book>,
    val context: Context
) : RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>() {
    class RecentlyViewedViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ivBook: ImageView = view.findViewById(R.id.ivBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedViewHolder {
        // Defining Custom Layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recently_viewed_recycler_view, parent, false)
        return RecentlyViewedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        // Inserting property value to BookVIewHolder Object
        val book = bookList[position]

        Glide.with(context)
            .load(book.cover?.front)
            .into(holder.ivBook)

        holder.ivBook.setOnClickListener {
            val intent = Intent(context, BookActivity::class.java)
            intent.putExtra("id", book._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
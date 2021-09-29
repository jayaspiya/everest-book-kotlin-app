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
import com.jayaspiya.everestbooks.response.BookResponse

class BookAdapter(
    private val bookList: MutableList<Book>,
    val context: Context
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val ivBook: ImageView = view.findViewById(R.id.ivBook)
        val mainRelativeLayout: RelativeLayout = view.findViewById(R.id.mainRelativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // Defining Custom Layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_recycler_view, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // Inserting property value to BookVIewHolder Object
        val book = bookList[position]
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.author
        holder.tvPrice.text = "Rs.${book.price}"

        Glide.with(context)
            .load(book.frontCover)
            .into(holder.ivBook)

        holder.mainRelativeLayout.setOnClickListener {
            val intent = Intent(context, BookActivity::class.java)
            intent.putExtra("id", book._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
package com.jayaspiya.everestbooks.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.BookActivity
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.Book
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    private val bookList: MutableList<Book>,
    val context: Context
) : RecyclerView.Adapter<CartAdapter.BookViewHolder>() {
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val ivBook: ImageView = view.findViewById(R.id.ivBook)
        val quantitySpinner: Spinner = view.findViewById(R.id.quantitySpinner)
        val btnView: Button = view.findViewById(R.id.btnView)
        val btnRemove: Button = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // Defining Custom Layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_recycler_view, parent, false)
        return BookViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // Inserting property value to BookVIewHolder Object
        val book = bookList[position]
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.author
        holder.tvPrice.text = "Rs.${book.price}"
        val qty = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, qty)
        holder.quantitySpinner.adapter = adapter

        Glide.with(context)
            .load(book.cover?.front)
            .into(holder.ivBook)

        holder.btnView.setOnClickListener {
            val intent = Intent(context, BookActivity::class.java)
            intent.putExtra("id", book._id)
            context.startActivity(intent)
        }
        holder.btnRemove.setOnClickListener {
            CoroutineScope(IO).launch {
                try {
                    val repository = UserRepository()
                    val response = repository.deleteFromCart(book._id)
                    println(response.message)
                } catch (ex: Exception) {
                    print(ex)
                }
            }
            bookList.remove(book)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
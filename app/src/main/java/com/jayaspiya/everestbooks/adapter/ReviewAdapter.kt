package com.jayaspiya.everestbooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.model.Review

class ReviewAdapter(
    private val reviewList: ArrayList<Review>,
    val context: Context
): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_recycler_view, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.tvUsername.text = review.user?.firstname?.capitalize()
        holder.tvRating.text = review.rating.toString()
        holder.tvDescription.text = review.description
        Glide.with(context)
            .load(review.user?.profile)
            .circleCrop()
            .into(holder.ivProfile)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}
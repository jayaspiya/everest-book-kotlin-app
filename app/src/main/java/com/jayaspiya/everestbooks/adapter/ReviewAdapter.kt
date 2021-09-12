package com.jayaspiya.everestbooks.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
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
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
        val ivStar1: ImageView = view.findViewById(R.id.ivStar1)
        val ivStar2: ImageView = view.findViewById(R.id.ivStar2)
        val ivStar3: ImageView = view.findViewById(R.id.ivStar3)
        val ivStar4: ImageView = view.findViewById(R.id.ivStar4)
        val ivStar5: ImageView = view.findViewById(R.id.ivStar5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_recycler_view, parent, false)
        return ReviewViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.tvUsername.text = review.user?.firstname?.capitalize()
        holder.tvDescription.text = review.description
        val stars = arrayOf(holder.ivStar1,holder.ivStar2,holder.ivStar3,holder.ivStar4,holder.ivStar5)
        val rating = review.rating!! - 1
        for (i in 0..rating){
            stars[i].imageTintList = ColorStateList.valueOf(context.resources.getColor(R.color.star))
        }
        Glide.with(context)
            .load(review.user?.profile)
            .circleCrop()
            .into(holder.ivProfile)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}
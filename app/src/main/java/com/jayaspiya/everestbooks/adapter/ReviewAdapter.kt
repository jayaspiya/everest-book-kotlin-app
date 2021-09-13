package com.jayaspiya.everestbooks.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.R
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.Review
import com.jayaspiya.everestbooks.repository.ReviewRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import androidx.core.content.ContextCompat
import com.jayaspiya.everestbooks.BookActivity
import androidx.core.content.ContextCompat.startActivity
import com.jayaspiya.everestbooks.model.Book


class ReviewAdapter(
    private val reviewList: MutableList<Review>,
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
        val ibSetting: ImageButton = view.findViewById(R.id.ibSetting)
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
        if(review.user?._id == ServiceBuilder.user?._id){
            holder.ibSetting.visibility = View.VISIBLE
        }
        else{
            holder.ibSetting.visibility = View.GONE
        }
        holder.tvDescription.text = review.description
        holder.ibSetting.setOnClickListener{
            val popMenu = PopupMenu(context, holder.ibSetting)
            popMenu.menuInflater.inflate(R.menu.review_menu, popMenu.menu)
//            TODO: Reload
            popMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menuEdit) {
                    val reviewDialog= Dialog(context)
                    reviewDialog.setContentView(R.layout.review_dialog)
                    reviewDialog.setCancelable(true)
                    reviewDialog.setCanceledOnTouchOutside(true)
                    reviewDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    val etReview: EditText =reviewDialog.findViewById(R.id.etReview)
                    val spinnerRating: Spinner =reviewDialog.findViewById(R.id.spinnerRating)
                    val btnSubmit: Button =reviewDialog.findViewById(R.id.btnSubmit)
                    val adapter = ArrayAdapter(
                        context, android.R.layout.simple_list_item_1, arrayOf(1,2,3,4,5)
                    )
                    spinnerRating.adapter = adapter
                    etReview.setText(review.description)
                    spinnerRating.setSelection(review.rating!! - 1)
                    reviewDialog.show()
                    btnSubmit.setOnClickListener{
                        if(TextUtils.isEmpty(etReview.text)){
                            etReview.requestFocus()
                            etReview.error = "Review name can not be empty."
                        }
                        else{
                            val reviewComment = etReview.text.toString()
                            val rating = spinnerRating.selectedItem.toString().toInt()
                            updateReview(review._id,reviewComment, rating)
                            reviewDialog.hide()
                        }
                    }
                } else if (item.itemId == R.id.menuDelete) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Delete")
                        .setMessage("Do you want to delete the review?")
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("Delete"){_,_->
                            deleteReview(review._id)
                        }
                        .setNegativeButton("Cancel"){_,_->
                            println("Canceled")
                        }
                    val alert = builder.create()
                    alert.setCancelable(true)
                    alert.setCanceledOnTouchOutside(true)
                    alert.show()
                }
                true
            }
            popMenu.show()
        }
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

    private fun updateReview(id: String,description: String, rating: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = ReviewRepository()
                val review = Review(rating = rating, description = description)
                repository.updateReview(id,review)
            }
            catch(ex: Exception){
                println(ex)
            }
        }
    }

    private fun deleteReview(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = ReviewRepository()
                repository.deleteReview(id)
            }
            catch(ex: Exception){
                println(ex)
            }
        }
    }
    override fun getItemCount(): Int {
        return reviewList.size
    }

}
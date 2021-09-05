package com.jayaspiya.everestbooks.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey()
    val _id: String = "",
    val title: String? = null,
    val author: String? = null,
    val isbn: String? = null,
    val synopsis: String? = null,
    val cover: Cover? = null,
    val price: Int? = 0,
    val reviews: ArrayList<Review> =ArrayList<Review>()
)

data class Cover(
    val side: String? = null,
    val front: String? = null,
    val back: String? = null,
)
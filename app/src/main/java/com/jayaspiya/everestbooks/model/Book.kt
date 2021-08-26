package com.jayaspiya.everestbooks.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val _id: String = "",
    val title: String? = null,
    val author: String? = null,
    val isbn: String? = null,
    val synopsis: String? = null,
    val cover: Cover? = null,
    val price: Int? = 0,
)

data class Cover(
    val side: String? = null,
    val front: String? = null,
    val back: String? = null,
)
package com.jayaspiya.everestbooks.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val Id: Int? = 0,
    val title: String? = null,
    val author: String? = null,
    val synopsis: String? = null,
    val imageUrl: String? = null,
    val price: Int? = 0,
)
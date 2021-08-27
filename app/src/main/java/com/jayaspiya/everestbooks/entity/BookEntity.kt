package com.jayaspiya.everestbooks.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity (
    @PrimaryKey()
    val _id: String = "",
    val title: String? = null,
    val author: String? = null,
    val isbn: String? = null,
    val synopsis: String? = null,
    val cover: String? = null,
    val price: Int? = 0,
)
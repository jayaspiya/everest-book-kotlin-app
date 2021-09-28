package com.jayaspiya.everestbooks.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity
data class Book(
    @PrimaryKey()
    @Nullable
    var _id: String = "",
    var title: String? = null,
    var author: String? = null,
    var isbn: String? = null,
    var synopsis: String? = null,
    var price: Int? = 0,
    var inCart: Boolean? = null,
    var frontCover:String?=null,
    @Ignore
    var cover: Cover? = null,
    @Ignore
    var reviews: MutableList<Review>? = null,
)

data class Cover(
    var side: String? = null,
    var front: String? = null,
    var back: String? = null,
)
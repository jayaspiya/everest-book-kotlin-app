package com.jayaspiya.everestbooks.response

import com.jayaspiya.everestbooks.model.Review

data class ReviewResponse (
    val message: String? = null,
    val success: Boolean? = null,
    val data:ArrayList<Review>? = null
)
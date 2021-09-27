package com.jayaspiya.everestbooks.model

import java.time.LocalDate

data class User(
    val _id: String? = "",
    val email: String? = null,
    val password: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val profile: String? = null,
    val gender: String? = null,
    val DOB: String? = null,
    val recentlyViewed:MutableList<Book>? = null,
    val reviews: Int = 0,
    val orders: Int = 0,
)
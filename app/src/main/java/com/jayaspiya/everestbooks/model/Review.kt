package com.jayaspiya.everestbooks.model

data class Review (
    val _id: String = "",
    val user: User? = null,
    val book: String? = null,
    val rating: Int? = null,
    val description: String? = null,
)
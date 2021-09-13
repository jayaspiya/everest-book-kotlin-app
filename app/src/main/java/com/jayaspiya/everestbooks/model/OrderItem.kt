package com.jayaspiya.everestbooks.model

data class OrderItem (
    val bookId: String? = "",
    var qty: Int? = 0,
    val price: Int? = 0,
)
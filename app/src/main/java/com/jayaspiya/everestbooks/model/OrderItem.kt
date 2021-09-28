package com.jayaspiya.everestbooks.model

data class OrderItem (
    val bookId: Book? = null,
    var qty: Int? = 0,
    val price: Int? = 0,
)
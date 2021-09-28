package com.jayaspiya.everestbooks.model

data class OrderBook (
    val _id: String = "",
    val status: String = "",
    var orderBook: MutableList<OrderItem>? = null
    )
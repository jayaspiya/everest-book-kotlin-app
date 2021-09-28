package com.jayaspiya.everestbooks.response

import com.jayaspiya.everestbooks.model.OrderBook

class OrderResponse (
    val message: String? = null,
    val success: Boolean? = null,
    val data: MutableList<OrderBook>? = null
)
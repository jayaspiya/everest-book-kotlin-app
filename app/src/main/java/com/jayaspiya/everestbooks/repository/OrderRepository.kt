package com.jayaspiya.everestbooks.repository

import com.jayaspiya.everestbooks.api.HttpRequestNetworkCall
import com.jayaspiya.everestbooks.api.OrderServices
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.OrderBook
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.response.BookResponse

class OrderRepository(): HttpRequestNetworkCall() {
    private val orderServices = ServiceBuilder.buildService(OrderServices::class.java)

    suspend fun placeOrder(orderBook:OrderBook): BookResponse{
        return myHttpRequestNetworkCall {
            orderServices.placeOrder(token = ServiceBuilder.token, orderBook = orderBook)
        }
    }

}

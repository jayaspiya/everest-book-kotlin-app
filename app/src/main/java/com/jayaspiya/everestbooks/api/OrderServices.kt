package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.OrderBook
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.response.BookResponse
import com.jayaspiya.everestbooks.response.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderServices {
    @GET("order/user")
    suspend fun getOrder(
        @Header("Authorization")
        token: String,
    ): Response<OrderResponse>

    @POST("order")
    suspend fun placeOrder(
        @Header("Authorization")
        token: String,
        @Body
        orderBook: OrderBook
    ):Response<BookResponse>
}
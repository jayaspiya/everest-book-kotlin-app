package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.OrderBook
import com.jayaspiya.everestbooks.model.OrderItem
import com.jayaspiya.everestbooks.response.BookResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderServices {
    @GET("book")
    suspend fun getBooks(): Response<BookResponse>

    @POST("order")
    suspend fun placeOrder(
        @Header("Authorization")
        token: String,
        @Body
        orderBook: OrderBook
    ):Response<BookResponse>
}
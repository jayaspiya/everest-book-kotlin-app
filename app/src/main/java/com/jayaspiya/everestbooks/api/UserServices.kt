package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.response.BookResponse
import com.jayaspiya.everestbooks.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserServices {
    @POST("user/register")
    suspend fun register(@Body user: User): Response<UserResponse>

    @POST("user/login")
    suspend fun login(@Body user: User): Response<UserResponse>

    @GET("user/cart")
    suspend fun getCart(
        @Header("Authorization")
        token: String
    ): Response<BookResponse>
}
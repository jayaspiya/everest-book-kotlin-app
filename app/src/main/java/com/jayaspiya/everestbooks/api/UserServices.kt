package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.response.BookResponse
import com.jayaspiya.everestbooks.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

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

    @POST("user/addtocart/{id}")
    suspend fun addToCart(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String
    ): Response<BookResponse>

    @PUT("user")
    suspend fun updateUser(
        @Header("Authorization")
        token: String,
        @Body
        user: User
    ): Response<UserResponse>

    @DELETE("user/deletefromcart/{id}")
    suspend fun deleteFromCart(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String
    ): Response<BookResponse>

    @GET("user/profile")
    suspend fun getProfile(
        @Header("Authorization")
        token: String,
    ): Response<UserResponse>

    @Multipart
    @PATCH("user/profile")
    suspend fun uploadImage(
        @Header("Authorization")
        token: String,
        @Part
        profile: MultipartBody.Part
    ): Response<UserResponse>
}
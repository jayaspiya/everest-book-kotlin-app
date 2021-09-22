package com.jayaspiya.everestbook_wearos.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserServices {

    @POST("user/login")
    suspend fun login(@Body user: User): Response<UserResponse>


    @GET("user/profile")
    suspend fun getProfile(
        @Header("Authorization")
        token: String,
    ): Response<UserResponse>

}
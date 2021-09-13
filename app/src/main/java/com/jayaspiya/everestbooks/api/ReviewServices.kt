package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.Review
import com.jayaspiya.everestbooks.response.ReviewResponse
import retrofit2.Response
import retrofit2.http.*

interface ReviewServices {
    @GET("review/{id}")
    suspend fun getReview(
        @Path("id")
        id: String
    ):Response<ReviewResponse>

    @POST("review/{id}")
    suspend fun addReview(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
        @Body
        review: Review
    ):Response<ReviewResponse>

    @PUT("review/{id}")
    suspend fun updateReview(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
        @Body
        review: Review
    ):Response<ReviewResponse>

    @DELETE("review/{id}")
    suspend fun deleteReview(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String
    ):Response<ReviewResponse>
}
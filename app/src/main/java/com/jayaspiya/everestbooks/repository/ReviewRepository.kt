package com.jayaspiya.everestbooks.repository

import android.content.Context
import com.jayaspiya.everestbooks.api.HttpRequestNetworkCall
import com.jayaspiya.everestbooks.api.ReviewServices
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.Review
import com.jayaspiya.everestbooks.response.ReviewResponse

class ReviewRepository(): HttpRequestNetworkCall() {
    private val reviewService = ServiceBuilder.buildService(ReviewServices::class.java)

    suspend fun getReview(id: String): ReviewResponse{
        return myHttpRequestNetworkCall {
            reviewService.getReview(id)
        }
    }

    suspend fun addReview(id: String, review: Review): ReviewResponse{
        return myHttpRequestNetworkCall {
            reviewService.addReview(token = ServiceBuilder.token, id = id, review = review)
        }
    }

    suspend fun updateReview(id: String, review: Review): ReviewResponse{
        return myHttpRequestNetworkCall {
            reviewService.updateReview(token = ServiceBuilder.token, id = id, review = review)
        }
    }

    suspend fun deleteReview(id: String): ReviewResponse{
        return myHttpRequestNetworkCall {
            reviewService.deleteReview(token = ServiceBuilder.token, id = id)
        }
    }

}

package com.jayaspiya.everestbooks.repository

import com.jayaspiya.everestbooks.api.HttpRequestNetworkCall
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.api.UserServices
import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.response.BookResponse
import com.jayaspiya.everestbooks.response.UserResponse
import okhttp3.MultipartBody

class UserRepository: HttpRequestNetworkCall() {
    private val userService = ServiceBuilder.buildService(UserServices::class.java)
    suspend fun registerUser(user: User): UserResponse {
        return myHttpRequestNetworkCall {
            userService.register(user)
        }
    }

    suspend fun loginUser(user: User): UserResponse {
        return myHttpRequestNetworkCall {
            userService.login(user)
        }
    }

    suspend fun getCart(): BookResponse{
        return myHttpRequestNetworkCall {
            userService.getCart(token = ServiceBuilder.token)
        }
    }

    suspend fun addToCart(id: String): BookResponse{
        return myHttpRequestNetworkCall {
            userService.addToCart(id = id, token = ServiceBuilder.token)
        }
    }

    suspend fun deleteFromCart(id: String): BookResponse{
        return myHttpRequestNetworkCall {
            userService.deleteFromCart(id = id, token = ServiceBuilder.token)
        }
    }

    suspend fun getProfile(): UserResponse{
        return myHttpRequestNetworkCall {
            userService.getProfile(token = ServiceBuilder.token)
        }
    }

    suspend fun uploadImage(body: MultipartBody.Part): UserResponse{
        return myHttpRequestNetworkCall {
            userService.uploadImage(token = ServiceBuilder.token, profile = body)
        }
    }
}
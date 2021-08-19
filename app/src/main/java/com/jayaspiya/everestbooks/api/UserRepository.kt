package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.response.UserResponse

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
}
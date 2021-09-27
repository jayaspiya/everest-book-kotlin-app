package com.jayaspiya.everestbook_wearos.api

class UserRepository: HttpRequestNetworkCall() {
    private val userService = ServiceBuilder.buildService(UserServices::class.java)

    suspend fun loginUser(user: User): UserResponse {
        return myHttpRequestNetworkCall {
            userService.login(user)
        }
    }

    suspend fun getProfile(token: String): UserResponse{
        return myHttpRequestNetworkCall {
            userService.getProfile(token = token)
        }
    }

}
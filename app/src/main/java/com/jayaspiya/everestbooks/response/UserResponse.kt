package com.jayaspiya.everestbooks.response

import com.jayaspiya.everestbooks.model.User

data class UserResponse (
    val accessToken: String? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val data: User? = null
)
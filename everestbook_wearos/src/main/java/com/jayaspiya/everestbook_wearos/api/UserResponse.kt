package com.jayaspiya.everestbook_wearos.api

data class UserResponse (
    val accessToken: String? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val data: User? = null
)
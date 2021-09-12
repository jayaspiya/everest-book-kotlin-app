package com.jayaspiya.everestbooks.model

import java.time.LocalDate

data class User(
    val _id: String? = "",
    val email: String? = null,
    val password: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val profile: String? = null,
    val gender: String? = null,
//    TODO: DOB Date
    val DOB: LocalDate? = null
)
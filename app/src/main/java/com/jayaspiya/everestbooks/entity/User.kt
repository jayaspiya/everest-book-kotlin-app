package com.jayaspiya.everestbooks.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate=true)
    val id: Int = 0,
    val email: String? = null,
    val password: String? = null,
)
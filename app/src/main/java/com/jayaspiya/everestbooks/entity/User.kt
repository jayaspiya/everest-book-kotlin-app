package com.jayaspiya.everestbooks.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
//data class User(
//    @PrimaryKey(autoGenerate=true)
//    var Userid: Int = 0,
//    var email: String? = null,
//    var password: String? = null,
//)

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val email: String? = null,
    val password: String? = null
)
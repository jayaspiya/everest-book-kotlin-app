package com.jayaspiya.everestbooks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jayaspiya.everestbooks.entity.User

@Dao
interface UserDAO {
    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM User where email=(:email) and password=(:password)")
    suspend fun loginUser(email: String, password: String)
}
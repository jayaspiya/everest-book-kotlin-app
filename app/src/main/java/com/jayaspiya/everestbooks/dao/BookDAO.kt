package com.jayaspiya.everestbooks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jayaspiya.everestbooks.entity.BookEntity

@Dao
interface BookDAO {
    @Insert
    suspend fun addBook(bookEntity:BookEntity)

    @Query("DELETE FROM BookEntity")
    suspend fun deleteBooks()
}
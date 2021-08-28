package com.jayaspiya.everestbooks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.jayaspiya.everestbooks.entity.BookEntity

@Dao
interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(bookEntity:BookEntity)

    @Query("DELETE FROM BookEntity")
    suspend fun deleteBooks()

//    @Query("SELECT * FROM BookEntity")
//    suspend fun getBooks()
}
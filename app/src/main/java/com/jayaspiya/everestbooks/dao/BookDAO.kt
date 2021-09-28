package com.jayaspiya.everestbooks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayaspiya.everestbooks.model.Book

@Dao
interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book:Book)

    @Query("SELECT * FROM Book")
    suspend fun getAllBooks():MutableList<Book>
}
package com.jayaspiya.everestbooks.repository

import android.content.Context
import com.jayaspiya.everestbooks.api.BookServices
import com.jayaspiya.everestbooks.api.HttpRequestNetworkCall
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.database.EverestDB
import com.jayaspiya.everestbooks.entity.BookEntity
import com.jayaspiya.everestbooks.response.BookResponse

class BookRepository(context: Context): HttpRequestNetworkCall() {
    private val bookService = ServiceBuilder.buildService(BookServices::class.java)
    val bookDao = EverestDB.getInstance(context).getBookDAO()

    suspend fun getBooks(): BookResponse{
        return myHttpRequestNetworkCall {
            bookService.getBooks()
        }
    }

    suspend fun getBook(id: String): BookResponse{
        return myHttpRequestNetworkCall {
            bookService.getBook(id=id)
        }
    }

    suspend fun delBookFromDB() {
        bookDao.deleteBooks()
    }

    suspend fun addBookFromDB(bookEn: BookEntity){
        bookDao.addBook(bookEn)
    }

//    suspend fun getBookFromDB(){
//        return bookDao.getBooks()
//    }
}

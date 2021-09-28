package com.jayaspiya.everestbooks.repository

import android.content.Context
import com.jayaspiya.everestbooks.api.BookServices
import com.jayaspiya.everestbooks.api.HttpRequestNetworkCall
import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.dao.BookDAO
import com.jayaspiya.everestbooks.entity.BookEntity
import com.jayaspiya.everestbooks.model.Book
import com.jayaspiya.everestbooks.response.BookResponse

class BookRepository(context: Context,private val bookDAO: BookDAO? = null): HttpRequestNetworkCall() {
    private val bookService = ServiceBuilder.buildService(BookServices::class.java)

    suspend fun getBooks(): MutableList<Book>?{
        try {
            val response = myHttpRequestNetworkCall {
                bookService.getBooks()
            }
            if (response.success == true) {
                addAllBookListToDB(response.data!!)
            }
            return bookDAO?.getAllBooks()
        } catch (ex: Exception) {
            print(ex)
        }
        return bookDAO?.getAllBooks()
    }

    suspend fun getBook(id: String): BookResponse{
        return myHttpRequestNetworkCall {
            bookService.getBook(token = ServiceBuilder.token,id=id)
        }
    }

    //Insert Product to Room Database
    private suspend fun addAllBookListToDB(bookList: MutableList<Book>) {
        for (book in bookList) {
            bookDAO?.addBook(book)
        }
    }
}

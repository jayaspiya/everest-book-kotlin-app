package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.response.BookResponse

class BookRepository:HttpRequestNetworkCall() {
    private val bookService = ServiceBuilder.buildService(BookServices::class.java)

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
}
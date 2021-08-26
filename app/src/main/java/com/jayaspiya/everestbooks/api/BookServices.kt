package com.jayaspiya.everestbooks.api

import com.jayaspiya.everestbooks.response.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookServices {
    @GET("book")
    suspend fun getBooks(): Response<BookResponse>

    @GET("book/view/{id}")
    suspend fun getBook(
        @Path("id") id: String
    ):Response<BookResponse>
}
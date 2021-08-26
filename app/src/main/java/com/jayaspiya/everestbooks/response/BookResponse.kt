package com.jayaspiya.everestbooks.response

import com.jayaspiya.everestbooks.model.Book

class BookResponse (
    val message: String? = null,
    val success: Boolean? = null,
    val data: ArrayList<Book>? = null
)
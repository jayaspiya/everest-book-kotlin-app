package com.jayaspiya.everestbooks.viewModel.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayaspiya.everestbooks.model.Book
import com.jayaspiya.everestbooks.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookViewModel(private val bookRepository: BookRepository): ViewModel(){
    var bookList = MutableLiveData<MutableList<Book>>()
    fun getAllBook() = viewModelScope.launch {
        var result:MutableList<Book>?=null
        withContext(Dispatchers.IO){
            val response = bookRepository.getBooks()
                result=response
        }
        bookList.value=result!!

    }

}


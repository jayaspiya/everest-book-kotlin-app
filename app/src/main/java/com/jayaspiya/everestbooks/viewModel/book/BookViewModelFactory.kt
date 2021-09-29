package com.jayaspiya.everestbooks.viewModel.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayaspiya.everestbooks.repository.BookRepository

class BookViewModelFactory (val repository: BookRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel?>create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(BookViewModel::class.java)){
            return BookViewModel(repository) as T
        }
        throw IllegalAccessException("")
    }
}
package com.jayaspiya.everestbooks.viewModel.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayaspiya.everestbooks.repository.ReviewRepository
import com.jayaspiya.everestreviews.viewModel.review.ReviewViewModel

class ReviewViewModelFactory (val repository: ReviewRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel?>create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)){
            return ReviewViewModel(repository) as T
        }
        throw IllegalAccessException("")
    }
}
package com.jayaspiya.everestreviews.viewModel.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayaspiya.everestbooks.model.Review
import com.jayaspiya.everestbooks.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReviewViewModel(private val reviewRepository: ReviewRepository): ViewModel(){
    var reviewList = MutableLiveData<MutableList<Review>>()
    fun getReview(id:String) = viewModelScope.launch {
        var result:MutableList<Review>?=null
        withContext(Dispatchers.IO){
            val response = reviewRepository.getReview(id)
                result=response.data
        }
//        reviewList.value=result!!

    }

}


package com.jayaspiya.everestorders.viewModel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayaspiya.everestbooks.model.OrderBook
import com.jayaspiya.everestbooks.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderViewModel(private val orderRepository: OrderRepository): ViewModel(){
    var orderList = MutableLiveData<MutableList<OrderBook>>()
    fun getOrder() = viewModelScope.launch {
        var result:MutableList<OrderBook>?=null
        withContext(Dispatchers.IO){
            val response = orderRepository.getOrder()
                result=response.data
        }
        orderList.value=result!!

    }

}


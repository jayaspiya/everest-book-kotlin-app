package com.jayaspiya.everestbooks.viewModel.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayaspiya.everestbooks.repository.OrderRepository
import com.jayaspiya.everestorders.viewModel.order.OrderViewModel

class OrderViewModelFactory (val repository: OrderRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel?>create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)){
            return OrderViewModel(repository) as T
        }
        throw IllegalAccessException("")
    }
}
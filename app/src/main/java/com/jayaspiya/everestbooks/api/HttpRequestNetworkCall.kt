package com.jayaspiya.everestbooks.api

import retrofit2.Response
import java.io.IOException

abstract class HttpRequestNetworkCall {
    suspend fun <T> myHttpRequestNetworkCall(anyFunction:suspend()-> Response<T>):T{
        val response = anyFunction.invoke()
        if (response.isSuccessful){
            return response.body()!!
        } else {
            val errorMessage = response.errorBody()?.string()
            val errorCode = "Error code: ${response.code()}"
            throw IOException("Error message: $errorMessage and \n Error code: $errorCode")
        }
    }
}
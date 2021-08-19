package com.jayaspiya.everestbooks.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://everest-book.herokuapp.com/"
    var token: String? = null
    // OKHttp Client
    private val okHttp = OkHttpClient.Builder().build()
    // Retrofit Builder
    private val retrofitBuilder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp)
    // Retrofit instance
    private val retrofit = retrofitBuilder.build()
    // Generic function
     fun <T> buildService(anyClass:Class<T>):T {
        return retrofit.create(anyClass)
     }
}
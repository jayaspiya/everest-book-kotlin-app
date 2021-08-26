package com.jayaspiya.everestbooks.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

object ServiceBuilder {
//    private const val BASE_URL = "https://everest-book.herokuapp.com/"
    private const val BASE_URL="http://10.0.2.2:5500/"
    var token: String? = null

    // create logging
    val logging =HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    // OKHttp Client
    private val okHttp = OkHttpClient.Builder().addInterceptor(logging).build()
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
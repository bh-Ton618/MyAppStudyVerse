package com.example.myappstudyverse.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Provides a singleton Retrofit instance for communicating with the NASA APOD API.
object RetrofitInstance {

    private const val BASE_URL = "https://api.nasa.gov/"

    // Lazily creates the Retrofit API service when it is first accessed.
    val api: NasaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }
}
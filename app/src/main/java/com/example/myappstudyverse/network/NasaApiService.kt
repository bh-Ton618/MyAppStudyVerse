package com.example.myappstudyverse.network

import com.example.myappstudyverse.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaApiService {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): ApodResponse

}

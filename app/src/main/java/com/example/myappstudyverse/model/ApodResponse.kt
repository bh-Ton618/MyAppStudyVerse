package com.example.myappstudyverse.model

// Represents the JSON response returned by the NASA APOD API.
data class ApodResponse(
    val title: String,
    val explanation: String,
    val url: String,
    val date: String,
    val copyright: String?,
    val media_type: String
)


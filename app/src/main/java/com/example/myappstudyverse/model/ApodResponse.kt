package com.example.myappstudyverse.model

data class ApodResponse(
    val title: String,
    val explanation: String,
    val url: String,
    val date: String,
    val copyright: String?,
    val media_type: String
)


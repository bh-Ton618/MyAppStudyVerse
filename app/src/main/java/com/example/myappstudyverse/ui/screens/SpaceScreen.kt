package com.example.myappstudyverse.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myappstudyverse.BuildConfig
import com.example.myappstudyverse.network.RetrofitInstance


@Composable
fun SpaceScreen() {
    var title by remember { mutableStateOf("Lade...") }

    LaunchedEffect(Unit) {

        val response = RetrofitInstance.api.getPictureOfTheDay(BuildConfig.NASA_API_KEY)
        title = response.title

    }
    Text(text = title)


}

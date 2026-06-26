package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),       //in my composable there are other kid elements /composables
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )

    {
        GreetingsSection()
        Spacer(
            modifier = Modifier.height(16.dp))

        MotivationalCard()
        Spacer(
            modifier = Modifier.height(16.dp))

        Button(
            onClick = {
            }
        ) {
            Text("Los geht's")
        }
    }
}

@Composable
fun GreetingsSection() {
    Text(
        text = "StudyVerse",
        modifier = Modifier.padding(16.dp),
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(
        modifier = Modifier.height(24.dp)
    )
    Text(
        text = "Willkommen zurück!"
    )
}

@Composable
fun MotivationalCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text ="Daily Motivation",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)


            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Stay focused!")

        }
    }

}
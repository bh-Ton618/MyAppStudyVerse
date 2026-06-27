package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp).padding(top = 32.dp),
        verticalArrangement = Arrangement.Top
    )

    {
        GreetingsSection()
        Spacer(
            modifier = Modifier.height(16.dp))

        MotivationalCard()
        Spacer(
            modifier = Modifier.height(16.dp))

        TodayOverview()

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
    Column() {
        Row {
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "Welcome back, Anna \uD83D\uDC4B"
                )
                Text(
                    text = "Saturday, June 27"
                )
            }
            Box(modifier = Modifier.size(40.dp).clickable {
                //TODO: Navigate to profile
            }
                .clip(CircleShape).background(Color.Gray),
                contentAlignment = Alignment.Center) {
                Text("A")
                    }

                }
            }
        }



@Composable
fun MotivationalCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().height(180.dp).padding(16.dp)) {

        Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
            ) {
        Column(modifier = Modifier.weight(1f).padding(16.dp),
            horizontalAlignment = Alignment.Start) {

            Text(
                text = "Daily Motivation",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Shoot for the moon.\nEven if you miss,\nyou'll land among the stars.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            }
            Box(modifier = Modifier.size(80.dp).padding(end = 16.dp),
                contentAlignment = Alignment.Center) {
                RocketIcon()
            }
        }
    }
}

@Composable
fun RocketIcon() {
    Text(text = "\uD83D\uDE80", fontSize = 40.sp)
}

@Composable
fun TodayOverview() {
    Column {
        Text(text = "Today's Overview")
        Spacer(modifier = Modifier.height(16.dp))
        Row{
            OverViewCard(modifier = Modifier.weight(1f), icon = "\uD83D\uDCCB", number = "12", title = "Task")
            Spacer(modifier = Modifier.width(8.dp))
            OverViewCard(modifier = Modifier.weight(1f), icon = "\uD83D\uDCDA", number = "2", title = "Classes")
            Spacer(modifier = Modifier.width(8.dp))
            OverViewCard(modifier = Modifier.weight(1f), icon = "\uD83D\uDCDD", number = "1", title = "Exam")
        }
    }

}

@Composable
fun OverViewCard(modifier: Modifier = Modifier,icon: String, number: String, title: String){

    Card(modifier = modifier.height(140.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.Gray),
                contentAlignment = Alignment.Center
                ) {
                Text(text = icon, fontSize = 24.sp)
            }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = number)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = title)

            }
        }
    }

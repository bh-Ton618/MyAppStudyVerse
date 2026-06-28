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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController,
                    modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp).padding(top = 32.dp),
        verticalArrangement = Arrangement.Top
    )

    {
        GreetingSection()
        Spacer(modifier = Modifier.height(16.dp))

        MotivationSection()
        Spacer(modifier = Modifier.height(16.dp))

        TodayOverviewSection()
        Spacer(modifier = Modifier.height(16.dp))

        UpComingSection()
        Spacer(modifier = Modifier.height(16.dp))

        BottomNavigationBar(navController)
    }
}

@Composable
fun GreetingSection() {
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
fun MotivationSection(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().height(180.dp)) {

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
fun TodayOverviewSection() {
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

    Card(modifier = modifier.height(120.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Gray),
                contentAlignment = Alignment.Center
                ) {
                Text(text = icon, fontSize = 20.sp)
            }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = number)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = title)

            }
        }
    }

@Composable
fun UpComingSection() {
    Column() {
        Text("Upcoming")
        Spacer(modifier = Modifier.height(16.dp))
        UpcomingCard(
            title = "Math Homework",
            subtitle = "Due Tomorrow 2PM",
            icon = "\uD83D\uDCD8"
        )
        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = "Physics Class",
            subtitle = "Tomorrow 4PM",
            icon = "\uD83E\uDDEA"
        )
        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = "Business Communication Exam",
            subtitle = "Tomorrow 8AM ",
            icon = "\uD83D\uDCDD"
        )

    }
}

@Composable
fun UpcomingCard(title: String, subtitle: String, icon: String) {
        Card() {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                    Text(text = icon,
                        fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitle,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Open"
                    )
                }
            }
        }
    }

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
    NavigationBarItem(
        selected = true,
        onClick = {
            navController.navigate("dashbaord")
        },
        icon = {
            Icon(Icons.Outlined.Home,
                contentDescription = "Home")
        }
    )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("tasks")
            },
            icon = {
                Icon(Icons.Outlined.AssignmentTurnedIn,
                    contentDescription = "Tasks")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("timetable")
            },
            icon = {
                Icon(Icons.Outlined.CalendarMonth,
                    contentDescription = "Timetable")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("notes")
            },
            icon = {
                Icon(Icons.Outlined.NoteAlt,
                    contentDescription = "Notes")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("space")
            },
            icon = {
                Icon(Icons.Outlined.AutoAwesome,
                    contentDescription = "Space")
            }
        )
    }
}



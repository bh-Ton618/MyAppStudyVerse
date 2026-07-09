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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappstudyverse.ui.components.AppFilterChip


data class TimetableEntry(
    val id: Int,
    val title: String,
    val room: String,
    val day: DayOfWeek,
    val startTime: String,  // later localTime/localDateTime
    val endTime: String // later localTime/localDateTime
)

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY
}

enum class TimetableView {
    WEEK,
    DAY
}


@Composable
fun TimetableHeaderArtWork() {
    //TODO: Insert artwork here later
}


@Composable
fun TimetableScreen() {

    var selectedView by remember { mutableStateOf(TimetableView.WEEK) }


    val timetableEntries = remember {
        mutableStateListOf(
            TimetableEntry(
                id = 1,
                title = "Math",
                room = "B-101",
                day = DayOfWeek.MONDAY,
                startTime = "8 AM",
                endTime = "10 AM"
            ),
            TimetableEntry(
                id = 2,
                title = "Informatics",
                room = "AB-100",
                day = DayOfWeek.TUESDAY,
                startTime = "12 PM",
                endTime = "2 PM"
            ),
            TimetableEntry(
                id = 3,
                title = "App Programming",
                room = "B-400",
                day = DayOfWeek.TUESDAY,
                startTime = "8 AM",
                endTime = "10 AM"
            ),
            TimetableEntry(
                id = 4,
                title = "Physics",
                room = "A-111",
                day = DayOfWeek.FRIDAY,
                startTime = "2 PM",
                endTime = "4 PM"
            ),
            TimetableEntry(
                id = 5,
                title = "Marketing",
                room = "B-404",
                day = DayOfWeek.THURSDAY,
                startTime = "11 AM",
                endTime = "4 PM"
            )
        )

    }

    val hours = listOf(
        "08 AM",
        "09 AM",
        "10 AM",
        "11 AM",
        "12 PM",
        "01 PM",
        "02 PM",
        "03 PM",
        "04 PM",
        "05 PM",
        "06 PM",
        "07 PM",
        "08 PM",
        "09 PM"
    )


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.offset(y = 24.dp),
                shape = CircleShape,
                containerColor = Color(0xFFA78BFA)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            TimetableHeaderArtWork()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Timetable", fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AppFilterChip(
                    text = "Week",
                    isSelected = selectedView == TimetableView.WEEK,
                    onClick = { selectedView = TimetableView.WEEK }

                )
                Spacer(modifier = Modifier.width(12.dp))

                AppFilterChip(
                    text = "Day",
                    isSelected = selectedView == TimetableView.DAY,
                    onClick = { selectedView = TimetableView.DAY }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            Column {
                // weekdays
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DayChip(day = "Mon", date = "13", isSelected = false, onClick = {})
                    DayChip(day = "Tue", date = "14", isSelected = true, onClick = {})
                    DayChip(day = "Wed", date = "15", isSelected = false, onClick = {})
                    DayChip(day = "Thu", date = "16", isSelected = false, onClick = {})
                    DayChip(day = "Fri", date = "17", isSelected = false, onClick = {})
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Column(modifier = Modifier.width(56.dp)) {
                            // time slots 8AM - 9PM
                            hours.forEach { hour ->
                                Text(
                                    text = hour,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)) {
                            // Timetable lines
                        }
                    }
                }
            }


        }
    }
}


@Composable
fun DayChip(day: String, date: String, isSelected: Boolean, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFA78BFA) else Color.Transparent)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = day, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = date, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

    }

}



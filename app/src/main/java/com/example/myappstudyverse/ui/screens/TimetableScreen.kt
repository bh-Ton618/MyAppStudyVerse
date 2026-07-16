package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
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

private fun mapDayToColumn(day: DayOfWeek): Int {
    return when (day) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
        //OR shorter:
        //private fun dayToColumn(day: DayOfWeek): Int {
        //return day.ordinal }
    }
}

private fun mapTimeToRow(time: String, hours: List<String>): Int {
    return hours.indexOf(time)
}


private fun formatLectureTime(start: String, end: String): String {
    val formattedStart = start.substringBefore(" ").trimStart('0')
    val formattedEnd = end.substringBefore(" ").trimStart('0')
    return "$formattedStart-$formattedEnd"
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
                startTime = "08 AM",
                endTime = "10 AM"
            ),
            TimetableEntry(
                id = 2,
                title = "Informatics",
                room = "AB-100",
                day = DayOfWeek.TUESDAY,
                startTime = "12 PM",
                endTime = "02 PM"
            ),
            TimetableEntry(
                id = 3,
                title = "App Programming",
                room = "B-400",
                day = DayOfWeek.TUESDAY,
                startTime = "08 AM",
                endTime = "10 AM"
            ),
            TimetableEntry(
                id = 4,
                title = "Physics",
                room = "A-111",
                day = DayOfWeek.FRIDAY,
                startTime = "02 PM",
                endTime = "04 PM"
            ),
            TimetableEntry(
                id = 5,
                title = "Marketing",
                room = "B-404",
                day = DayOfWeek.THURSDAY,
                startTime = "11 AM",
                endTime = "04 PM"
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

    //shared height for each timetable hour slot to keep
    // the timeline, grid and lecture cards aligned.
    val hourSlotHeight = 56.dp


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
                    start = 16.dp,
                    end = 16.dp,
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
                // Weekdays ->
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp)
                    ) {
                        // times left site ->
                        Column(modifier = Modifier.width(44.dp)) {
                            // Time slots 8AM - 9PM ->
                            hours.forEach { hour ->
                                Text(
                                    text = hour,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }
                        // Grid ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                            //.padding(start = 1.dp)
                        ) {
                            // Horizontal timetable lines ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                hours.forEach { _ ->
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    )
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                            }
                            //Vertical timetable lines ->
                            Row(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                DayOfWeek.entries.forEachIndexed { index, day ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                    ) {
                                        if (index < DayOfWeek.entries.lastIndex) {
                                            VerticalDivider(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .fillMaxHeight(),
                                                thickness = 1.dp,
                                                color = MaterialTheme.colorScheme.outlineVariant
                                            )

                                        }
                                    }
                                }

                            }
                            val entry = timetableEntries.first()
                            val column = mapDayToColumn(entry.day)
                            val row = mapTimeToRow(entry.startTime, hours)

                            val endRow = mapTimeToRow(entry.endTime, hours)
                            val duration = endRow - row
                            val lectureHeight = hourSlotHeight * duration

                            val columnWidth = 70.dp
                            val xOffset = column * columnWidth
                            val yOffset = row * hourSlotHeight

                            LectureCard(
                                modifier = Modifier.offset(x = xOffset, y = yOffset),
                                entry = entry,
                                lectureHeight = lectureHeight
                            )
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
            .size(width = 42.dp, height = 64.dp)
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


@Composable
fun LectureCard(modifier: Modifier = Modifier, entry: TimetableEntry, lectureHeight: Dp) {
    Card(
        modifier = modifier
            .width(70.dp)
            .height(lectureHeight)
            .padding(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
            Text(text = entry.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = entry.room, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = formatLectureTime(start = entry.startTime, end = entry.endTime),
                fontSize = 13.sp
            )
        }
    }
}

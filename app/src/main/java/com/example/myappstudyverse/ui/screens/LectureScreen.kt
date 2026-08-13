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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CoPresent
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.myappstudyverse.ui.components.AppFilterChip


data class Lecture(
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

enum class LectureView {
    WEEK,
    DAY
}


@Composable
fun LectureHeaderArtWork() {
    // TODO: Insert artwork here later
}

// Converts weekdays into timetable column indices.
private fun mapDayToColumn(day: DayOfWeek): Int {
    return when (day) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
    }
}

// Converts lecture start times into timetable row indices.
private fun mapTimeToRow(time: String, hours: List<String>): Int {
    return hours.indexOf(time)
}


// Formats lecture times for compact display inside lecture cards.
private fun formatLectureTime(start: String, end: String): String {
    val formattedStart = start.substringBefore(" ").trimStart('0')
    val formattedEnd = end.substringBefore(" ").trimStart('0')
    return "$formattedStart-$formattedEnd"
}


// Main timetable screen providing week and day views of scheduled lectures.
@Composable
fun LectureScreen(navController: NavHostController) {

    // Stores the currently selected timetable view.
    var selectedView by remember { mutableStateOf(LectureView.WEEK) }


    // Sample timetable data used for demonstrating the timetable layout.
    val lectureEntries = remember {
        mutableStateListOf(
            Lecture(
                id = 1,
                title = "Mathematics",
                room = "B-101",
                day = DayOfWeek.MONDAY,
                startTime = "08 AM",
                endTime = "10 AM"
            ),
            Lecture(
                id = 2,
                title = "Informatics",
                room = "AB-100",
                day = DayOfWeek.TUESDAY,
                startTime = "12 PM",
                endTime = "02 PM"
            ),
            Lecture(
                id = 3,
                title = "App Programming 2.",
                room = "B-400",
                day = DayOfWeek.TUESDAY,
                startTime = "08 AM",
                endTime = "10 AM"
            ),
            Lecture(
                id = 4,
                title = "Physics",
                room = "A-111",
                day = DayOfWeek.FRIDAY,
                startTime = "02 PM",
                endTime = "04 PM"
            ),
            Lecture(
                id = 5,
                title = "Marketing",
                room = "B-404",
                day = DayOfWeek.THURSDAY,
                startTime = "11 AM",
                endTime = "04 PM"
            ),
            Lecture(
                id = 6,
                title = " Immun - Biology",
                room = "B-101",
                day = DayOfWeek.MONDAY,
                startTime = "10 AM",
                endTime = "12 PM"
            ),
            Lecture(
                id = 7,
                title = "Business and Communications",
                room = "B-101",
                day = DayOfWeek.WEDNESDAY,
                startTime = "12 PM",
                endTime = "02 PM"
            ),
            Lecture(
                id = 8,
                title = "Web Development",
                room = "B-101",
                day = DayOfWeek.WEDNESDAY,
                startTime = "02 PM",
                endTime = "04 PM"
            ),
            Lecture(
                id = 9,
                title = "App Android Exam :D",
                room = "A-002",
                day = DayOfWeek.WEDNESDAY,
                startTime = "08 PM",
                endTime = "09 PM"
            ),
            Lecture(
                id = 10,
                title = "IT Security",
                room = "B-101",
                day = DayOfWeek.TUESDAY,
                startTime = "07 PM",
                endTime = "09 PM"
            ),
        )

    }

    // Defines the lecture's hourly time slots.
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

    // Shared height for each lecture slot to keep the timeline, grid and cards aligned.
    val hourSlotHeight = 49.dp


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("lectureDetail/new") },
                modifier = Modifier.offset(y = 24.dp),
                shape = CircleShape,
                containerColor = Color(0xFFA78BFA)
            ) {
                Icon(
                    imageVector = Icons.Default.CoPresent,
                    contentDescription = "Add lecture",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            LectureHeaderArtWork()

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
                    isSelected = selectedView == LectureView.WEEK,
                    onClick = { selectedView = LectureView.WEEK }

                )
                Spacer(modifier = Modifier.width(12.dp))

                AppFilterChip(
                    text = "Day",
                    isSelected = selectedView == LectureView.DAY,
                    onClick = { selectedView = LectureView.DAY }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Switch between Composables Week/Day View based on selected Chip ->
            if (selectedView == LectureView.WEEK) {
                WeekView(
                    lectureEntries = lectureEntries,
                    hours = hours,
                    hourSlotHeight = hourSlotHeight,
                    navController = navController
                )
            } else {
                DayView(
                    lectureEntries = lectureEntries,
                    hours = hours,
                    hourSlotHeight = hourSlotHeight,
                    navController = navController
                )
            }
        }
    }
}


// Displays the weekly timetable with lectures positioned in a timetable grid.
@Composable
fun WeekView(
    lectureEntries: List<Lecture>,
    hours: List<String>,
    hourSlotHeight: Dp,
    navController: NavHostController
) {

    Column {
        // Displays the weekday selector.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DayChip(day = "Mon", date = "13", isSelected = false, onClick = {})
            DayChip(day = "Tue", date = "14", isSelected = false, onClick = {})
            DayChip(day = "Wed", date = "15", isSelected = true, onClick = {})
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
                // Times left site.
                Column(modifier = Modifier.width(44.dp)) {
                    // Time slots 8AM - 9PM.
                    hours.forEach { hour ->
                        Text(
                            text = hour,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                // Draws the timetable / lecture grid and places lecture cards at their calculated positions.
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 1.dp, end = 1.dp)
                ) {
                    // Horizontal timetable lines.
                    Column(modifier = Modifier.fillMaxWidth()) {
                        hours.forEach { _ ->
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                            Spacer(modifier = Modifier.height(48.dp))
                        }
                    }
                    // Vertical timetable lines.
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        DayOfWeek.entries.forEachIndexed { index, day ->
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
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
                    lectureEntries.forEach { lecture ->

                        // Calculates the lecture position within the lecture grid.
                        val column = mapDayToColumn(lecture.day)
                        val row = mapTimeToRow(lecture.startTime, hours)

                        val endRow = mapTimeToRow(lecture.endTime, hours)
                        val duration = endRow - row
                        val lectureHeight = hourSlotHeight * duration

                        val columnWidth = 70.dp
                        val xOffset = column * columnWidth
                        val yOffset = row * hourSlotHeight

                        LectureCard(
                            modifier = Modifier.offset(x = xOffset, y = yOffset),
                            lecture = lecture,
                            onClick = { navController.navigate("lectureDetail/${lecture.id}") },
                            lectureHeight = lectureHeight
                        )
                    }
                }
            }
        }
    }

}


// Displays the timetable for a single day.
@Composable
fun DayView(
    lectureEntries: List<Lecture>,
    hours: List<String>,
    hourSlotHeight: Dp,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Wednesday",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // time slots from 8AM - 9 PM ->
            Column(
                modifier = Modifier.width(50.dp)
            ) {
                hours.forEach { hour ->
                    Box(
                        modifier = Modifier.height(hourSlotHeight)
                    ) {
                        Text(
                            text = hour,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                // Horizontal lines ->
                Column {
                    hours.forEach { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(hourSlotHeight)
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
                lectureEntries
                    // Displays only lectures scheduled for the selected day.
                    .filter { lecture -> lecture.day == DayOfWeek.WEDNESDAY }
                    .forEach { lecture ->

                        val row = mapTimeToRow(lecture.startTime, hours)
                        val endRow = mapTimeToRow(lecture.endTime, hours)
                        val duration = endRow - row
                        val lectureHeight = hourSlotHeight * duration

                        val yOffset = row * hourSlotHeight

                        LectureCard(
                            modifier = Modifier.offset(y = yOffset),
                            lecture = lecture,
                            lectureHeight = lectureHeight,
                            onClick = { navController.navigate("lectureDetail/${lecture.id}") },
                            isDayView = true

                        )
                    }
            }
        }
    }

}


// Reusable chip representing a weekday in the timetable.
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


// Reusable card displaying lecture information within the timetable.
@Composable
fun LectureCard(
    modifier: Modifier = Modifier,
    lecture: Lecture,
    lectureHeight: Dp,
    onClick: () -> Unit,
    isDayView: Boolean = false
) {

    // Adjusts the lecture size depending on the selected timetable view (Week/Day).
    val cardWidth =
        if (isDayView) 260.dp
        else 69.dp

    val lectureTitleFontSize =
        if (isDayView) 15.sp
        else 13.sp


    Card(
        modifier = modifier
            .width(cardWidth)
            .height(lectureHeight)
            .padding(2.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)) {
            Text(
                text = lecture.title,
                fontWeight = FontWeight.Bold,
                fontSize = lectureTitleFontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = lecture.room, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = formatLectureTime(start = lecture.startTime, end = lecture.endTime),
                fontSize = 12.sp
            )
        }
    }
}

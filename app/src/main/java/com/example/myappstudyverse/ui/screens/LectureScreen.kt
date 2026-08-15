package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.LectureRepository
import com.example.myappstudyverse.ui.components.AppFilterChip
import com.example.myappstudyverse.ui.viewmodel.LectureViewModel
import com.example.myappstudyverse.ui.viewmodel.LectureViewModelFactory
import java.time.LocalDate
import java.time.DayOfWeek as JavaDayOfWeek


data class Lecture(
    val id: Int,
    val title: String,
    val room: String,
    val day: DayOfWeek,
    val startTime: String,
    val endTime: String,
    val lecturer: String?
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

    val timeWithoutAMPM = time.substringBefore(" ")
    val amOrPm = time.substringAfter(" ")
    val hour = timeWithoutAMPM.substringBefore(":").toInt()
    val minute = if (timeWithoutAMPM.contains(":")) {
        timeWithoutAMPM.substringAfter(":").toInt()
    } else {
        0
    }

    val hourIn24Format = when {
        amOrPm == "AM" && hour == 12 -> 0
        amOrPm == "AM" -> hour
        amOrPm == "PM" && hour == 12 -> 12
        else -> hour + 12
    }

    return ((hourIn24Format * 60 + minute) - (8 * 60)) / 30
}


// Formats lecture times for compact display inside lecture cards.
private fun formatLectureTime(
    start: String,
    end: String,
    isDayView: Boolean
): String {

    if (isDayView) {
        return "$start-$end"
    }

    val startTime = start.substringBefore(" ").trimStart('0')
    val endTime = end.substringBefore(" ").trimStart('0')

    return "$startTime-$endTime"
}


// Main timetable screen providing week and day views of scheduled lectures.
@Composable
fun LectureScreen(navController: NavHostController) {

    // Provides access to the local database and manages lecture data through the ViewModel.
    val context = LocalContext.current

    val lectureRepository = remember {
        LectureRepository(
            DatabaseProvider
                .getDatabase(context)
                .lectureDao()
        )
    }

    val lectureViewModel: LectureViewModel = viewModel(
        factory = LectureViewModelFactory(lectureRepository)
    )


    // Stores the currently selected timetable view.
    var selectedView by remember { mutableStateOf(LectureView.WEEK) }


    // Loads lectures from the local database when the screen is first displayed.
    LaunchedEffect(Unit) {
        lectureViewModel.loadLectures()
    }

    val lectureEntries by lectureViewModel.lectures.collectAsState()


    // Determines the current weekday and the dates of the current week.
    val currentDate = LocalDate.now()

    val currentDay = when (currentDate.dayOfWeek) {
        JavaDayOfWeek.MONDAY -> DayOfWeek.MONDAY
        JavaDayOfWeek.TUESDAY -> DayOfWeek.TUESDAY
        JavaDayOfWeek.WEDNESDAY -> DayOfWeek.WEDNESDAY
        JavaDayOfWeek.THURSDAY -> DayOfWeek.THURSDAY
        JavaDayOfWeek.FRIDAY -> DayOfWeek.FRIDAY
        JavaDayOfWeek.SATURDAY -> null
        JavaDayOfWeek.SUNDAY -> null
    }

    val startOfWeek = currentDate.minusDays(
        currentDate.dayOfWeek.value - JavaDayOfWeek.MONDAY.value.toLong()
    )

    val mondayDate = startOfWeek.dayOfMonth.toString()
    val tuesdayDate = startOfWeek.plusDays(1).dayOfMonth.toString()
    val wednesdayDate = startOfWeek.plusDays(2).dayOfMonth.toString()
    val thursdayDate = startOfWeek.plusDays(3).dayOfMonth.toString()
    val fridayDate = startOfWeek.plusDays(4).dayOfMonth.toString()


    // Defines the lecture's hourly time slots.
    val hours = listOf(
        "08 AM",
        "08:30",
        "09 AM",
        "09:30",
        "10 AM",
        "10:30",
        "11 AM",
        "11:30",
        "12 PM",
        "12:30",
        "01 PM",
        "01:30",
        "02 PM",
        "02:30",
        "03 PM",
        "03:30",
        "04 PM",
        "04:30",
        "05 PM",
        "05:30",
        "06 PM",
        "06:30",
        "07 PM",
        "07:30",
        "08 PM",
        "08:30",
        "09 PM"
    )

    // Shared height for each 30-minute lecture slot to keep the timeline, grid and cards aligned.
    val hourSlotHeight = 24.5.dp


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
                    navController = navController,
                    currentDay = currentDay,
                    mondayDate = mondayDate,
                    tuesdayDate = tuesdayDate,
                    wednesdayDate = wednesdayDate,
                    thursdayDate = thursdayDate,
                    fridayDate = fridayDate,

                    onDeleteLecture = { lecture ->
                        lectureViewModel.deleteLecture(lecture)
                    }
                )
            } else {
                DayView(
                    lectureEntries = lectureEntries,
                    hours = hours,
                    hourSlotHeight = hourSlotHeight,
                    navController = navController,
                    currentDay = currentDay,
                    onDeleteLecture = { lecture ->
                        lectureViewModel.deleteLecture(lecture)
                    }
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
    navController: NavHostController,
    currentDay: DayOfWeek?,
    mondayDate: String,
    tuesdayDate: String,
    wednesdayDate: String,
    thursdayDate: String,
    fridayDate: String,
    onDeleteLecture: (Lecture) -> Unit
) {

    Column {
        // Displays the weekday selector.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DayChip(
                day = "Mon",
                date = mondayDate,
                isSelected = currentDay == DayOfWeek.MONDAY,
                onClick = {}
            )
            DayChip(
                day = "Tue",
                date = tuesdayDate,
                isSelected = currentDay == DayOfWeek.TUESDAY,
                onClick = {}
            )
            DayChip(
                day = "Wed",
                date = wednesdayDate,
                isSelected = currentDay == DayOfWeek.WEDNESDAY,
                onClick = {}
            )
            DayChip(
                day = "Thu",
                date = thursdayDate,
                isSelected = currentDay == DayOfWeek.THURSDAY,
                onClick = {}
            )
            DayChip(
                day = "Fri",
                date = fridayDate,
                isSelected = currentDay == DayOfWeek.FRIDAY,
                onClick = {}
            )
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
                            fontWeight = FontWeight.SemiBold,
                            color = if (hour.contains(":30"))
                                Color.LightGray
                            else
                                Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(0.dp))
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
                        hours.forEach { hour ->
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = if (hour.contains(":30"))
                                    Color.LightGray.copy(alpha = 0.5f)
                                else
                                    MaterialTheme.colorScheme.outlineVariant
                            )
                            Spacer(modifier = Modifier.height(23.5.dp))
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
                            onDeleteClick = {
                                onDeleteLecture(lecture)
                            },
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
    navController: NavHostController,
    currentDay: DayOfWeek?,
    onDeleteLecture: (Lecture) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = when (currentDay) {
                DayOfWeek.MONDAY -> "Monday"
                DayOfWeek.TUESDAY -> "Tuesday"
                DayOfWeek.WEDNESDAY -> "Wednesday"
                DayOfWeek.THURSDAY -> "Thursday"
                DayOfWeek.FRIDAY -> "Friday"
                null -> "It's the weekend ✨"
            },
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
                            fontWeight = FontWeight.SemiBold,
                            color = if (hour.contains(":30"))
                                Color.LightGray
                            else
                                Color.Unspecified
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
                    hours.forEach { hour ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(hourSlotHeight)
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.align(Alignment.TopCenter),
                                color = if (hour.contains(":30"))
                                    Color.LightGray.copy(alpha = 0.5f)
                                else
                                    MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
                lectureEntries
                    // Displays only lectures scheduled for the selected day.
                    .filter { lecture -> lecture.day == currentDay }
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
                            isDayView = true,
                            onDeleteClick = { onDeleteLecture(lecture) }

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
    onDeleteClick: () -> Unit,
    isDayView: Boolean = false
) {

    var isContextMenuExpanded by remember { mutableStateOf(false) }

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
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    isContextMenuExpanded = true
                }
            ),
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
                text = formatLectureTime(
                    start = lecture.startTime,
                    end = lecture.endTime,
                    isDayView = isDayView
                ),
                fontSize = 12.sp
            )
        }

        DropdownMenu(
            expanded = isContextMenuExpanded,
            onDismissRequest = {
                isContextMenuExpanded = false
            },
            offset = DpOffset(
                x = (-40).dp,
                y = (-65).dp
            ),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFA78BFA)
            )
        ) {
            DropdownMenuItem(
                modifier = Modifier
                    .height(30.dp)
                    .width(100.dp),
                text = {
                    Text(
                        text = "Delete",
                        color = Color.DarkGray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFA78BFA)
                    )
                },
                onClick = {
                    isContextMenuExpanded = false
                    onDeleteClick()
                }
            )
        }
    }
}

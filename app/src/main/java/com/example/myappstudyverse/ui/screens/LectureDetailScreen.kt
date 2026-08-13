package com.example.myappstudyverse.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myappstudyverse.ui.components.AppFilterChip

@Composable
fun LectureDetailScreen(
    navController: NavHostController,
    lectureId: Int?
) {

    var lectureTitle by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf(DayOfWeek.MONDAY) }
    var lectureRoom by remember { mutableStateOf("") }
    var lectureStartTime by remember { mutableStateOf("08:00") }
    var lectureEndTime by remember { mutableStateOf("10:00") }
    var lecturer by remember { mutableStateOf("") }

    var isStartTimeMenuExpanded by remember { mutableStateOf(false) }
    var isEndTimeMenuExpanded by remember { mutableStateOf(false) }

    val timeOptions = listOf(
        "08:00", "08:30",
        "09:00", "09:30",
        "10:00", "10:30",
        "11:00", "11:30",
        "12:00", "12:30",
        "13:00", "13:30",
        "14:00", "14:30",
        "15:00", "15:30",
        "16:00", "16:30",
        "17:00", "17:30",
        "18:00", "18:30",
        "19:00", "19:30",
        "20:00", "20:30",
        "21:00"
    )

    val validEndTimeOptions = timeOptions.filter { time ->
        time > lectureStartTime
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 35.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .height(52.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF7C4DFF))
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                // TODO: Later delete
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                            Text(
                                text = "Delete",
                                color = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(22.dp)
                                .background(
                                    Color.White.copy(alpha = 0.5f)
                                )
                        )

                        TextButton(
                            onClick = {
                                // TODO: Later Save
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "Save",
                                tint = Color.White
                            )
                            Text(
                                text = "Save",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 8.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEDE9FE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CoPresent,
                        contentDescription = null,
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = if (lectureId == null)
                        "NEW LECTURE"
                    else
                        "LECTURE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA78BFA)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Lecture Title",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            BasicLectureTextField(
                value = lectureTitle,
                onValueChange = { newLectureTitle ->
                    lectureTitle = newLectureTitle
                },
                placeholder = "Enter Lecture Title"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Day",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AppFilterChip(
                    text = "Mon",
                    isSelected = selectedDay == DayOfWeek.MONDAY,
                    onClick = {
                        selectedDay = DayOfWeek.MONDAY
                    }
                )

                AppFilterChip(
                    text = "Tue",
                    isSelected = selectedDay == DayOfWeek.TUESDAY,
                    onClick = {
                        selectedDay = DayOfWeek.TUESDAY
                    }
                )

                AppFilterChip(
                    text = "Wed",
                    isSelected = selectedDay == DayOfWeek.WEDNESDAY,
                    onClick = {
                        selectedDay = DayOfWeek.WEDNESDAY
                    }
                )

                AppFilterChip(
                    text = "Thu",
                    isSelected = selectedDay == DayOfWeek.THURSDAY,
                    onClick = {
                        selectedDay = DayOfWeek.THURSDAY
                    }
                )

                AppFilterChip(
                    text = "Fri",
                    isSelected = selectedDay == DayOfWeek.FRIDAY,
                    onClick = {
                        selectedDay = DayOfWeek.FRIDAY
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Room",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            BasicLectureTextField(
                value = lectureRoom,
                onValueChange = { newLectureRoom ->
                    lectureRoom = newLectureRoom
                },
                placeholder = "Enter Room"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Start Time
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Start Time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    isStartTimeMenuExpanded = true
                                }
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 16.dp
                                )
                        ) {
                            Text(
                                text = lectureStartTime,
                                fontSize = 16.sp
                            )
                        }

                        DropdownMenu(
                            expanded = isStartTimeMenuExpanded,
                            onDismissRequest = {
                                isStartTimeMenuExpanded = false
                            },
                            offset = DpOffset(
                                x = 33.dp,
                                y = (-15).dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .height(150.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                timeOptions.forEach { time ->

                                    DropdownMenuItem(
                                        text = {
                                            Text(text = time)
                                        },
                                        onClick = {

                                            lectureStartTime = time

                                            if (lectureEndTime <= time) {
                                                val startIndex =
                                                    timeOptions.indexOf(time)

                                                if (
                                                    startIndex >= 0 &&
                                                    startIndex + 1 < timeOptions.size
                                                ) {
                                                    lectureEndTime =
                                                        timeOptions[startIndex + 1]
                                                }
                                            }

                                            isStartTimeMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // End Time
                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "End Time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    isEndTimeMenuExpanded = true
                                }
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 16.dp
                                )
                        ) {
                            Text(
                                text = lectureEndTime,
                                fontSize = 16.sp
                            )
                        }

                        DropdownMenu(
                            expanded = isEndTimeMenuExpanded,
                            onDismissRequest = {
                                isEndTimeMenuExpanded = false
                            },
                            offset = DpOffset(
                                x = 33.dp,
                                y = (-15).dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .height(150.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                validEndTimeOptions.forEach { time ->

                                    DropdownMenuItem(
                                        text = {
                                            Text(text = time)
                                        },
                                        onClick = {
                                            lectureEndTime = time
                                            isEndTimeMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Lecturer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            BasicLectureTextField(
                value = lecturer,
                onValueChange = { newLecturer ->
                    lecturer = newLecturer
                },
                placeholder = "Enter Lecturer"
            )
        }
    }
}


@Composable
private fun BasicLectureTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 16.dp
            ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 16.sp,
                        color = Color.LightGray
                    )
                }

                innerTextField()
            }
        }
    )
}
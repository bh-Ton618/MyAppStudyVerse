package com.example.myappstudyverse.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.TaskAlt
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myappstudyverse.ui.components.AppFilterChip

@Composable
fun TaskDetailScreen(
    navController: NavHostController,
    taskId: Int?,
    taskType: TaskType?
) {

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("02.08.2026") }
    var taskProfessor by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf<Priority?>(null) }
    var selectedExamType by remember { mutableStateOf<ExamType?>(ExamType.WRITTEN) }
    var isTaskDone by remember { mutableStateOf(false) }

    val isExam = taskType == TaskType.EXAM
    val isNew = taskId == null

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
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .height(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (isTaskDone)
                                    Color(0xFFA78BFA)
                                else
                                    Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFFA78BFA),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clickable {
                                isTaskDone = !isTaskDone
                            }
                            .padding(
                                horizontal = 12.dp,
                                vertical = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when {
                                isExam && isTaskDone -> "Mark as unpassed"
                                isExam -> "Mark as passed"
                                isTaskDone -> "Mark as undone"
                                else -> "Mark as done"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isTaskDone)
                                Color.White
                            else
                                Color(0xFFA78BFA)
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))

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
                                .background(Color.White.copy(alpha = 0.5f))
                        )
                        TextButton(
                            onClick = {

                                // TODO: later Save
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
                onClick = { navController.popBackStack() },
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(
                modifier = Modifier.height(
                    if (isExam) 4.dp else 18.dp
                )
            )
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
                        imageVector = if (isExam)
                            Icons.Default.CoPresent
                        else
                            Icons.Outlined.TaskAlt,
                        contentDescription = null,
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = when {
                        isExam && isNew -> "NEW EXAM"
                        isExam -> "EXAM"
                        isNew -> "NEW TASK"
                        else -> "TASK"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA78BFA)
                )
            }
            Spacer(
                modifier = Modifier.height(
                    if (isExam) 12.dp else 18.dp
                )
            )
            Text(
                text = if (isExam)
                    "Exam Title"
                else
                    "Task Title",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            BasicTextField(
                value = taskTitle,
                onValueChange = { newTaskTitle ->
                    taskTitle = newTaskTitle
                },
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
                        if (taskTitle.isEmpty()) {
                            Text(
                                text = if (isExam)
                                    "Enter Exam Title"
                                else
                                    "Enter Task Title",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }

                        innerTextField()
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(
                    if (isExam) 16.dp else 24.dp
                )
            )
            Text(
                text = "Description",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            BasicTextField(
                value = taskDescription,
                onValueChange = { newTaskDescription ->
                    taskDescription = newTaskDescription
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
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
                decorationBox = { innerTextField ->
                    Box {
                        if (taskDescription.isEmpty()) {
                            Text(
                                text = "Enter Description",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }

                        innerTextField()
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(
                    if (isExam) 16.dp else 24.dp
                )
            )
            Text(
                text = if (isExam)
                    "Exam Date"
                else
                    "Due Date",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            BasicTextField(
                value = taskDueDate,
                onValueChange = { newTaskDueDate ->
                    taskDueDate = newTaskDueDate
                },
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
                )
            )

            if (isExam) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Professor",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                BasicTextField(
                    value = taskProfessor,
                    onValueChange = { newTaskProfessor ->
                        taskProfessor = newTaskProfessor
                    },
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
                            if (taskProfessor.isEmpty()) {
                                Text(
                                    text = "Enter Professor",
                                    fontSize = 16.sp,
                                    color = Color.LightGray
                                )
                            }

                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Exam Type",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppFilterChip(
                        text = "Written",
                        isSelected = selectedExamType == ExamType.WRITTEN,
                        onClick = {
                            selectedExamType = ExamType.WRITTEN
                        }
                    )
                    AppFilterChip(
                        text = "Oral",
                        isSelected = selectedExamType == ExamType.ORAL,
                        onClick = {
                            selectedExamType = ExamType.ORAL
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

            } else {

                Spacer(modifier = Modifier.height(28.dp))
            }
            Text(
                text = "Priority",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.height(
                    if (isExam) 8.dp else 16.dp
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppFilterChip(
                    text = "Low",
                    isSelected = selectedPriority == Priority.LOW,
                    onClick = {
                        selectedPriority = Priority.LOW
                    }
                )
                AppFilterChip(
                    text = "Medium",
                    isSelected = selectedPriority == Priority.MEDIUM,
                    onClick = {
                        selectedPriority = Priority.MEDIUM
                    }
                )
                AppFilterChip(
                    text = "High",
                    isSelected = selectedPriority == Priority.HIGH,
                    onClick = {
                        selectedPriority = Priority.HIGH
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No Priority",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .size(
                        width = 115.dp,
                        height = 40.dp
                    )
                    .clip(RoundedCornerShape(50.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFA78BFA),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .background(
                        if (selectedPriority == null)
                            Color(0xFFA78BFA)
                        else
                            Color.White
                    )
                    .clickable {
                        selectedPriority = null
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "None",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedPriority == null)
                        Color.White
                    else
                        Color.DarkGray
                )
            }
        }
    }
}
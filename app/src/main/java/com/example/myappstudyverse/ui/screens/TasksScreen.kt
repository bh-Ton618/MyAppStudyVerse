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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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


data class Task(
    val title: String,
    val dueDate: String,
    val priority: String,
    val description: String,
    var isDone: Boolean
)


@Composable
fun TasksScreen() {
    var selectedFilterChip by remember { mutableStateOf("All") }
    val taskList = listOf(
        Task("TestTask", "Due: 02.08.2026", "HIGH", "blablabla :)", true),
        Task("TestTask", "Due: 29.07.2026", "HIGH", "tralalla", false),
        Task("TestTask2", "Due: 01.08.2026", "MEDIUM", "shalala:P", false),
        Task("TestTask3", "Due: 15.07.2026", "LOW", "CioaCiao ;D", false),
        Task("TestTask4", "Due: 21.07.2026", "HIGH", "gettingBoring:P", false),
        Task("TestTask5", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask6", "Due: 22.07.2026", "LOW", "lastOne ~", true),
        Task("TestTask7", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask8", "Due: 22.07.2026", "LOW", "lastOne ~", true),
        Task("TestTask9", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask10", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask11", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask12", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask13", "Due: 22.07.2026", "LOW", "lastOne ~", true),
        Task("TestTask14", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask15", "Due: 22.07.2026", "LOW", "lastOne ~", true),
        Task("TestTask16", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask17", "Due: 22.07.2026", "LOW", "lastOne ~", false),
        Task("TestTask18", "Due: 22.07.2026", "LOW", "lastOne ~", false)
    )


    val filteredTaskList = when (selectedFilterChip) {
        "All" -> taskList
        "to Do" -> taskList.filter { task -> !task.isDone }
        "Done" -> taskList.filter { task -> task.isDone }
        else -> taskList
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "My Tasks",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            TaskFilterChip(
                text = "All",
                isSelected = selectedFilterChip == "All",
                onClick = {
                    selectedFilterChip = "All"
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskFilterChip(
                text = "to Do",
                isSelected = selectedFilterChip == "to Do",
                onClick = {
                    selectedFilterChip = "to Do"
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskFilterChip(
                text = "Done",
                isSelected = selectedFilterChip == "Done",
                onClick = {
                    selectedFilterChip = "Done"
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Scrollbare Liste aller Task
        // Nur sichtbare Elemente werden gerendert (Lazy Loading)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredTaskList) { task ->
                TaskCard(task = task)
            }
        }
    }
}


@Composable
fun TaskFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 115.dp, height = 40.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(
                if (isSelected) Color.Gray
                else Color.LightGray
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

    }
}

@Composable
fun TaskCard(task: Task) {

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(
                        if (task.isDone) Color(0xFFA78BFA)
                        else Color.Gray
                    )
                    .clickable {
                        task.isDone = !task.isDone
                    }
            ) {
                if (task.isDone) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Default.Check,
                        tint = Color.White,
                        contentDescription = if (task.isDone) "Task done"
                        else "Task open"
                    )

                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, fontWeight = FontWeight.Bold)
                Text(text = task.dueDate, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open"
            )
        }
    }
}
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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


data class Task(
    val id: Int,
    val title: String,
    val dueDate: String,
    val description: String,
    val priority: Priority,
    var isDone: Boolean
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}


@Composable
fun TasksScreen() {
    //Stores the currently selected task filter: All, to Do, Done
    var selectedFilterChip by remember { mutableStateOf("All") }

    var isSearchOpen by remember { mutableStateOf(false) }

    var searchText by remember { mutableStateOf("") }

    val taskList = remember {
        mutableStateListOf(
            Task(id = 1, "TestTask1", "Due: 02.08.2026", "kasfaf", priority = Priority.HIGH, true),
            Task(
                id = 2,
                "TestTask2",
                "Due: 29.07.2026",
                "fsfwefwe",
                priority = Priority.LOW,
                false
            ),
            Task(
                id = 3,
                "TestTask3",
                "Due: 01.08.2026",
                "wefwefwef",
                priority = Priority.MEDIUM,
                false
            ),
            Task(
                id = 4,
                "TestTask4",
                "Due: 15.07.2026",
                "wefwefwef",
                priority = Priority.HIGH,
                false
            ),
            Task(id = 5, "TestTask5", "Due: 21.07.2026", "wfwef", priority = Priority.LOW, false),
            Task(
                id = 6,
                "TestTask6",
                "Due: 22.07.2026",
                "Lwefwefw",
                priority = Priority.LOW,
                false
            ),
            Task(id = 7, "TestTask7", "Due: 22.07.2026", "wefwef", priority = Priority.HIGH, true),
            Task(
                id = 8,
                "TestTask8",
                "Due: 22.07.2026",
                "wefwef",
                priority = Priority.MEDIUM,
                false
            ),
            Task(id = 9, "TestTask9", "Due: 22.07.2026", "efwef", priority = Priority.MEDIUM, true),
            Task(
                id = 10,
                "TestTask10",
                "Due: 22.07.2026",
                "efwef",
                priority = Priority.MEDIUM,
                false
            )
        )
    }


//Filters the task list based on the selected filter.
    val filteredTaskList = when (selectedFilterChip) {
        "All" -> taskList
        "to Do" -> taskList.filter { task -> !task.isDone }
        "Done" -> taskList.filter { task -> task.isDone }
        else -> taskList
    }


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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "My Tasks",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        isSearchOpen = !isSearchOpen
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .offset(y = 2.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                }
            }

            if (isSearchOpen) {

            }

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

            // Scrollable list of all task
            // Nur sichtbare Elemente werden gerendert (Lazy Loading)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    items = filteredTaskList,
                    key = { task -> task.id }
                ) { task ->
                    TaskCard(
                        task = task,
                        onTaskChecked = {
                            val taskIndex = taskList.indexOfFirst {
                                it.id == task.id
                            }
                            if (taskIndex != -1) {
                                taskList[taskIndex] = task.copy(
                                    isDone = !task.isDone
                                )
                            }

                        }
                    )
                }
            }
        }
    }
}

//Reusable filter chip for selecting a task category.
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
                if (isSelected) Color(0xFFA78BFA)
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

//Displays a single card item.
@Composable
fun TaskCard(
    task: Task,
    onTaskChecked: () -> Unit
) {
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
                        onTaskChecked()
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

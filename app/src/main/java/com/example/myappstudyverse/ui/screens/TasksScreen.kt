package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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


data class Task(
    val id: Int,
    val title: String,
    val dueDate: String = " ",
    val description: String = " ",
    val priority: Priority? = null,
    var isDone: Boolean
)

enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}

enum class TaskSortOption {
    PRIORITY,
    DUE_DATE,
    TITLE,
    CREATED_DATE
}

@Composable
fun TaskHeaderArtWork() {
    //TODO: Insert artwork here later
}

@Composable
fun TasksScreen() {
    //Stores the currently selected task filter: All, to Do, Done
    var selectedFilterChip by remember { mutableStateOf("All") }
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf(TaskSortOption.PRIORITY) }


    val taskList = remember {
        mutableStateListOf(
            Task(
                id = 1,
                "StudySwiftUI",
                "02.08.2026",
                "Program my own Apple Task App",
                priority = Priority.HIGH,
                true
            ),
            Task(
                id = 2,
                "Mathe Homework",
                "29.07.2026",
                "learn!",
                priority = Priority.LOW,
                false
            ),
            Task(
                id = 3,
                "Physics Assignment",
                "01.08.2026",
                "can be done later :) ",
                priority = Priority.MEDIUM,
                false
            ),
            Task(
                id = 4,
                "Buy Groceries",
                "15.07.2026",
                "best when I have time this week",
                priority = Priority.HIGH,
                false
            ),
            Task(
                id = 5,
                "Prepare presentation",
                "21.07.2026",
                "about black wholes",
                priority = Priority.LOW,
                false
            ),
            Task(
                id = 6,
                "Call AOK",
                "22.07.2026",
                "to ask about insurance policy",
                priority = Priority.LOW,
                false
            ),
            Task(
                id = 7,
                "Gym Session",
                "22.07.2026",
                "wefwef",
                priority = Priority.HIGH,
                true
            ),
            Task(
                id = 8,
                "NASA Project",
                "22.07.2026",
                "study about moon artemis mission",
                priority = Priority.MEDIUM,
                false
            ),
            Task(
                id = 9,
                "Study Kotlin",
                "22.07.2026",
                "efwef",
                priority = Priority.MEDIUM,
                true
            ),
            Task(
                id = 10,
                "APP Project",
                "22.07.2026",
                "finish 3 screen by the end of the week",
                priority = Priority.MEDIUM,
                false
            )
        )
    }

    // Step 1: Sort tasks ->
    val sortedTaskList = when (selectedSortOption) {

        TaskSortOption.PRIORITY ->
            taskList.sortedBy { task -> task.priority }

        TaskSortOption.DUE_DATE ->
            taskList.sortedBy { task -> task.dueDate }

        TaskSortOption.TITLE ->
            taskList.sortedBy { task -> task.title }

        TaskSortOption.CREATED_DATE ->
            taskList.sortedBy { task -> task.id }

    }

//Step 2: Filters the task list based on the selected filter.
    val filteredTaskList = when (selectedFilterChip) {
        "All" -> sortedTaskList
        "to Do" -> sortedTaskList.filter { task -> !task.isDone }
        "Done" -> sortedTaskList.filter { task -> task.isDone }
        else -> sortedTaskList
    }.filter { task ->
        task.title.contains(searchText, ignoreCase = true) || task.description.contains(
            searchText,
            ignoreCase = true
        )

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
                .statusBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            TaskHeaderArtWork()

            Spacer(modifier = Modifier.height(16.dp))

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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                    IconButton(
                        onClick = {
                            isFilterMenuExpanded = !isFilterMenuExpanded
                        }
                    )
                    {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .offset(y = 2.dp),
                            imageVector = Icons.Outlined.FilterList,
                            contentDescription = "Sort"
                        )
                    }
                    DropdownMenu(
                        expanded = isFilterMenuExpanded,
                        onDismissRequest = {
                            isFilterMenuExpanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Priority") },
                            onClick = {
                                selectedSortOption = TaskSortOption.PRIORITY
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Due Date") },
                            onClick = {
                                selectedSortOption = TaskSortOption.PRIORITY
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Title") },
                            onClick = {
                                selectedSortOption = TaskSortOption.TITLE
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Created Date") },
                            onClick = {
                                selectedSortOption = TaskSortOption.TITLE
                                isFilterMenuExpanded = false
                            }
                        )

                    }

                }


            }

            if (isSearchOpen) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(18.dp),
                    placeholder = { Text("Search tasks...") },
                    singleLine = true
                )

            }
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                AppFilterChip(
                    text = "All",
                    isSelected = selectedFilterChip == "All",
                    onClick = {
                        selectedFilterChip = "All"
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppFilterChip(
                    text = "to Do",
                    isSelected = selectedFilterChip == "to Do",
                    onClick = {
                        selectedFilterChip = "to Do"
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppFilterChip(
                    text = "Done",
                    isSelected = selectedFilterChip == "Done",
                    onClick = {
                        selectedFilterChip = "Done"
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Displays the filtered list of tasks.
            // Only visible items are rendered to improve performance (Lazy Loading)
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

//Displays a single card item.
@Composable
fun TaskCard(
    task: Task,
    onTaskChecked: () -> Unit
) {
    val priorityColor = when (task.priority) {
        Priority.HIGH -> Color(0xFF7C3AED)
        Priority.MEDIUM -> Color(0xFFA78BFA)
        Priority.LOW -> Color(0xFFD8B4FE)
        null -> Color.Transparent

    }
    Card(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(priorityColor)
            )

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
                    Text(text = "Due: ${task.dueDate}", fontSize = 14.sp)
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Open"
                )
            }
        }
    }
}

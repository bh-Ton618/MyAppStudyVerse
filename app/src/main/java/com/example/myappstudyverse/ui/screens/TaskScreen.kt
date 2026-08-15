package com.example.myappstudyverse.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.EditNote
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.TaskRepository
import com.example.myappstudyverse.ui.components.AppFilterChip
import com.example.myappstudyverse.ui.viewmodel.TaskViewModel
import com.example.myappstudyverse.ui.viewmodel.TaskViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


data class Task(
    val id: Int,
    val title: String,
    val dueDate: String = " ",
    val description: String = " ",
    val priority: Priority? = null,
    var isDone: Boolean,
    val type: TaskType = TaskType.TASK,
    val createdAt: Long = System.currentTimeMillis(),

    val professor: String? = null,
    val examType: ExamType? = null
)

enum class TaskType {
    TASK,
    EXAM
}

enum class ExamType {
    WRITTEN,
    ORAL
}

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

// Main task management screen with search, filtering and sorting functionality.
@Composable
fun TasksScreen(navController: NavHostController) {


    //Provides access to the local database and manages task data through the ViewModel.
    val context = LocalContext.current

    val taskRepository = remember {
        TaskRepository(
            DatabaseProvider
                .getDatabase(context)
                .taskDao()
        )
    }

    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(taskRepository)
    )


    // Stores the current UI state for filtering, searching and sorting tasks.
    var selectedFilterChip by remember { mutableStateOf("All") }
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf(TaskSortOption.PRIORITY) }

    // Controls the expanded state of the Floating Action Button menu.
    var isFabExpanded by remember { mutableStateOf(false) }


    // Loads tasks from the local database when screen is first displayed.
    LaunchedEffect(Unit) {
        taskViewModel.loadTasks()
    }
    val taskList by taskViewModel.tasks.collectAsState()


    // Converts the due date text into a date value for sorting.
    fun getDueDateValue(dueDate: String): LocalDate? {
        if (dueDate.isBlank()) {
            return null
        }

        return try {
            LocalDate.parse(
                dueDate,
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
        } catch (e: DateTimeParseException) {
            null
        }
    }


    // Sorts the task list based on the selected sort option.
    val sortedTaskList = when (selectedSortOption) {

        TaskSortOption.PRIORITY ->
            taskList.sortedBy { task ->
                when (task.priority) {
                    Priority.HIGH -> 0
                    Priority.MEDIUM -> 1
                    Priority.LOW -> 2
                    null -> 3
                }
            }

        TaskSortOption.DUE_DATE ->
            taskList.sortedWith(
                compareBy<Task> { task ->
                    getDueDateValue(task.dueDate) == null
                }.thenBy { task ->
                    getDueDateValue(task.dueDate)
                }
            )

        TaskSortOption.TITLE ->
            taskList.sortedBy { task -> task.title.lowercase() }

        TaskSortOption.CREATED_DATE ->
            taskList.sortedBy { task -> task.createdAt }
    }


    // Filters the sorted task list by completion status and search query.
    val filteredTaskList = when (selectedFilterChip) {
        "All" -> sortedTaskList
        "to Do" -> sortedTaskList.filter { task -> !task.isDone }
        "Done" -> sortedTaskList.filter { task -> task.isDone }
        else -> sortedTaskList
    }.filter { task ->
        task.title.contains(searchText, ignoreCase = true) ||
                task.description.contains(searchText, ignoreCase = true)
    }


    Scaffold(
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        isFabExpanded = !isFabExpanded
                    },
                    modifier = Modifier
                        .offset(y = 28.dp)
                        .alpha(if (isFabExpanded) 0f else 1f),
                    shape = CircleShape,
                    containerColor = Color(0xFFA78BFA)
                ) {
                    Icon(
                        imageVector = Icons.Default.AssignmentTurnedIn,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier.offset(y = 20.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isFabExpanded) {

                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFA78BFA),
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .clickable {
                                    isFabExpanded = false
                                    navController.navigate("taskDetail/new?type=TASK")
                                }
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 10.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckBox,
                                contentDescription = null,
                                tint = Color(0xFFA78BFA),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "New Task",
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFA78BFA),
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .clickable {
                                    isFabExpanded = false
                                    navController.navigate("taskDetail/new?type=EXAM")
                                }
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 10.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.EditNote,
                                contentDescription = null,
                                tint = Color(0xFFA78BFA),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "New Exam",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                        ) {
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
                                    selectedSortOption = TaskSortOption.DUE_DATE
                                    isFilterMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Title A-Z") },
                                onClick = {
                                    selectedSortOption = TaskSortOption.TITLE
                                    isFilterMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Created Date") },
                                onClick = {
                                    selectedSortOption = TaskSortOption.CREATED_DATE
                                    isFilterMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Displays the search field only when search mode is enabled.
                if (isSearchOpen) {

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp),
                        placeholder = {
                            Text("Search tasks...")
                        },
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


                // Displays the filtered task list.
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = filteredTaskList,
                        key = { task -> task.id }
                    ) { task ->
                        TaskCard(
                            task = task,
                            onTaskChecked = {
                                taskViewModel.updateTask(
                                    task.copy(
                                        isDone = !task.isDone
                                    )
                                )
                            },
                            onTaskDeleted = {
                                taskViewModel.deleteTask(task)
                            },
                            onTaskClick = {
                                navController.navigate("taskDetail/${task.id}")
                            }
                        )
                    }
                }
            }

            // Invisible overlay which closes the FAB menu
            // When the user clicks anywhere outside the menu.
            if (isFabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            isFabExpanded = false
                        }
                )
            }
        }
    }
}


// Reusable card displaying a single task, and its current completion status.
@Composable
fun TaskCard(
    task: Task,
    onTaskChecked: () -> Unit,
    onTaskDeleted: () -> Unit,
    onTaskClick: () -> Unit
) {

    var isTaskLongPressMenuExpanded by remember {
        mutableStateOf(false)
    }

    // Maps each priority level to a corresponding indicator color.
    val priorityColor = when (task.priority) {

        Priority.HIGH -> Color(0xFF7C3AED)
        Priority.MEDIUM -> Color(0xFFA78BFA)
        Priority.LOW -> Color(0xFFD8B4FE)
        null -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onTaskClick()
                },
                onLongClick = {
                    isTaskLongPressMenuExpanded = true
                }
            ),
        border = if (task.type == TaskType.EXAM) {
            BorderStroke(width = 1.dp, color = Color(0xFFA78BFA))
        } else {
            null
        }
    ) {
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
                            if (task.isDone) {
                                Color(0xFFA78BFA)
                            } else {
                                Color.Gray
                            }
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
                            contentDescription = if (task.isDone) {
                                "Task done"
                            } else {
                                "Task open"
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (task.dueDate.isNotBlank()) {
                            "Due: ${task.dueDate}"
                        } else {
                            " "
                        },
                        fontSize = 14.sp,
                        color = if (task.dueDate.isNotBlank()) {
                            Color.Unspecified
                        } else {
                            Color.Transparent
                        }
                    )
                }
            }
        }
        DropdownMenu(
            expanded = isTaskLongPressMenuExpanded,
            onDismissRequest = {
                isTaskLongPressMenuExpanded = false
            },
            offset = DpOffset(
                x = (-40).dp,
                y = (-65).dp
            ),
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = Color(0xFFA78BFA)
            )
        ) {
            DropdownMenuItem(
                modifier = Modifier.height(30.dp),

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
                    isTaskLongPressMenuExpanded = false
                    onTaskDeleted()
                }
            )
        }
    }
}
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.TaskRepository
import com.example.myappstudyverse.ui.components.AppFilterChip
import com.example.myappstudyverse.ui.theme.Gold
import com.example.myappstudyverse.ui.theme.Gridline
import com.example.myappstudyverse.ui.theme.InputFieldText
import com.example.myappstudyverse.ui.theme.NavigationSurface
import com.example.myappstudyverse.ui.theme.OffWhite1
import com.example.myappstudyverse.ui.theme.OffWhite2
import com.example.myappstudyverse.ui.theme.PurplePrimary
import com.example.myappstudyverse.ui.theme.SaveAndDeleteButton
import com.example.myappstudyverse.ui.theme.textFieldInputHint
import com.example.myappstudyverse.ui.viewmodel.TaskViewModel
import com.example.myappstudyverse.ui.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun TaskDetailScreen(
    navController: NavHostController,
    taskId: Int?,
    taskType: TaskType?
) {

    // Design colors used specifically in this screen.
    // The accent matches the currently selected AppFilterChip.
    val accentColor = Color(0xFFA78BFA)

    // Dark, subtle background for the icon container.
    val iconSurfaceColor = Color(0xFF26203F)

    // Slightly muted text color for placeholders.
    val placeholderColor = Color(0xFFB8BCD0)

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var examProfessor by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf<Priority?>(null) }
    var selectedExamType by remember { mutableStateOf<ExamType?>(ExamType.WRITTEN) }
    var isTaskDone by remember { mutableStateOf(false) }
    var hasLoadedExistingTask by remember { mutableStateOf(false) }

    // Controls the short message shown saving an existing task.
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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

    val taskList by taskViewModel.tasks.collectAsState()

    val isExam = taskType == TaskType.EXAM
    val isNew = taskId == null


    // Loads the task data from the local database when editing an existing task.
    LaunchedEffect(Unit) {
        taskViewModel.loadTasks()
    }

    LaunchedEffect(taskList, taskId) {
        if (!isNew && !hasLoadedExistingTask) {
            val existingTask = taskList.find { task ->
                task.id == taskId
            }

            if (existingTask != null) {
                taskTitle = existingTask.title
                taskDescription = existingTask.description
                taskDueDate = existingTask.dueDate
                examProfessor = existingTask.professor ?: ""
                selectedPriority = existingTask.priority
                selectedExamType = existingTask.examType
                isTaskDone = existingTask.isDone
                hasLoadedExistingTask = true
            }
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SnackbarHost(
                    hostState = snackbarHostState
                ) { snackbarData ->
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(48.dp)
                            .offset(y = 80.dp)
                            .border(
                                width = 0.5.dp,
                                color = OffWhite1.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(
                                color = NavigationSurface,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            color = OffWhite2
                        )
                    }
                }
            }
        },
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
                                    SaveAndDeleteButton
                                else
                                    Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = PurplePrimary.copy(alpha = 0.6f),
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
                                OffWhite2
                            else
                                PurplePrimary
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Row(
                        modifier = Modifier
                            .height(52.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(SaveAndDeleteButton)
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                if (!isNew) {
                                    val existingTask = taskList.find { task ->
                                        task.id == taskId
                                    }

                                    if (existingTask != null) {
                                        taskViewModel.deleteTask(existingTask)
                                    }
                                }

                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = OffWhite1
                            )

                            Text(
                                text = "Delete",
                                color = OffWhite1
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

                                val hasRequiredInput = if (isExam) {
                                    taskTitle.isNotBlank() && taskDueDate.isNotBlank()
                                } else {
                                    taskTitle.isNotBlank()
                                }

                                if (hasRequiredInput) {
                                    val task = Task(
                                        id = if (isNew) 0 else taskId,
                                        title = taskTitle,
                                        dueDate = taskDueDate,
                                        description = taskDescription,
                                        priority = selectedPriority,
                                        isDone = isTaskDone,
                                        type = if (isExam)
                                            TaskType.EXAM
                                        else
                                            TaskType.TASK,
                                        createdAt = if (isNew) {
                                            System.currentTimeMillis()
                                        } else {
                                            taskList.find { existingTask ->
                                                existingTask.id == taskId
                                            }?.createdAt
                                                ?: System.currentTimeMillis()
                                        },
                                        professor = if (isExam) {
                                            examProfessor.takeIf { professorText ->
                                                professorText.isNotBlank()
                                            }
                                        } else {
                                            null
                                        },
                                        examType = if (isExam) {
                                            selectedExamType
                                        } else {
                                            null
                                        }
                                    )

                                    if (isNew) {
                                        taskViewModel.addTask(task)
                                        navController.popBackStack()
                                    } else {
                                        taskViewModel.updateTask(task)

                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Saved")
                                        }
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "Save",
                                tint = OffWhite1
                            )

                            Text(
                                text = "Save",
                                color = OffWhite1
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
                    if (isNew) {
                        val hasRequiredInput = if (isExam) {
                            taskTitle.isNotBlank() && taskDueDate.isNotBlank()
                        } else {
                            taskTitle.isNotBlank()
                        }

                        if (hasRequiredInput) {
                            val task = Task(
                                id = 0,
                                title = taskTitle,
                                dueDate = taskDueDate,
                                description = taskDescription,
                                priority = selectedPriority,
                                isDone = isTaskDone,
                                type = if (isExam)
                                    TaskType.EXAM
                                else
                                    TaskType.TASK,
                                createdAt = System.currentTimeMillis(),
                                professor = if (isExam) {
                                    examProfessor.takeIf { professorText ->
                                        professorText.isNotBlank()
                                    }
                                } else {
                                    null
                                },
                                examType = if (isExam) {
                                    selectedExamType
                                } else {
                                    null
                                }
                            )

                            taskViewModel.addTask(task)
                        }
                    } else {
                        val hasRequiredInput = if (isExam) {
                            taskTitle.isNotBlank() && taskDueDate.isNotBlank()
                        } else {
                            taskTitle.isNotBlank()
                        }

                        if (hasRequiredInput) {
                            val existingTask = taskList.find { task ->
                                task.id == taskId
                            }

                            if (existingTask != null) {
                                val task = Task(
                                    id = taskId,
                                    title = taskTitle,
                                    dueDate = taskDueDate,
                                    description = taskDescription,
                                    priority = selectedPriority,
                                    isDone = isTaskDone,
                                    type = if (isExam)
                                        TaskType.EXAM
                                    else
                                        TaskType.TASK,
                                    createdAt = existingTask.createdAt,
                                    professor = if (isExam) {
                                        examProfessor.takeIf { professorText ->
                                            professorText.isNotBlank()
                                        }
                                    } else {
                                        null
                                    },
                                    examType = if (isExam) {
                                        selectedExamType
                                    } else {
                                        null
                                    }
                                )

                                taskViewModel.updateTask(task)
                            }
                        }
                    }

                    navController.popBackStack()
                },
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
                        .background(
                            if (isExam) Gold.copy(alpha = 0.2f) else PurplePrimary.copy(alpha = 0.2f)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isExam) Gold.copy(alpha = 0.3f) else PurplePrimary.copy(
                                alpha = 0.3f
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isExam)
                            Icons.Default.CoPresent
                        else
                            Icons.Outlined.TaskAlt,
                        contentDescription = null,
                        tint = if (isExam) Gold else PurplePrimary,
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
                    color = if (isExam) Gold.copy(alpha = 0.8f) else PurplePrimary.copy(alpha = 0.8f)
                )
            }

            Spacer(
                modifier = Modifier.height(
                    if (isExam) 12.dp else 18.dp
                )
            )

            Text(
                text = if (isExam)
                    "Exam Title *"
                else
                    "Task Title *",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OffWhite2
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
                    .background(
                        color = NavigationSurface,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = Gridline,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 16.dp
                    ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = InputFieldText
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (taskTitle.isEmpty()) {
                            Text(
                                text = if (isExam)
                                    "Enter title"
                                else
                                    "Enter title",
                                fontSize = 16.sp,
                                color = textFieldInputHint
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
                fontWeight = FontWeight.Bold,
                color = OffWhite2
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
                    .background(
                        color = NavigationSurface,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = Gridline,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 16.dp
                    ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = InputFieldText
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (taskDescription.isEmpty()) {
                            Text(
                                text = "Enter description",
                                fontSize = 16.sp,
                                color = textFieldInputHint
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
                    "Exam Date *"
                else
                    "Due Date",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OffWhite2
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
                    .background(
                        color = NavigationSurface,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = Gridline,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 16.dp
                    ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = InputFieldText
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (taskDueDate.isEmpty()) {
                            Text(
                                text = if (isExam)
                                    "DD.MM.YYYY"
                                else
                                    "DD.MM.YYYY",
                                fontSize = 16.sp,
                                color = textFieldInputHint
                            )
                        }

                        innerTextField()
                    }
                }
            )

            if (isExam) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Professor",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = OffWhite2
                )

                Spacer(modifier = Modifier.height(6.dp))

                BasicTextField(
                    value = examProfessor,
                    onValueChange = { newTaskProfessor ->
                        examProfessor = newTaskProfessor
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = NavigationSurface,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Gridline,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 16.dp
                        ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = InputFieldText
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {
                            if (examProfessor.isEmpty()) {
                                Text(
                                    text = "Enter Professor",
                                    fontSize = 16.sp,
                                    color = textFieldInputHint
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
                    fontWeight = FontWeight.Bold,
                    color = OffWhite2
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
                fontWeight = FontWeight.Bold,
                color = OffWhite2
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
                fontWeight = FontWeight.Medium,
                color = OffWhite2
            )

            Spacer(modifier = Modifier.height(6.dp))

            AppFilterChip(
                text = "None",
                isSelected = selectedPriority == null,
                onClick = {
                    selectedPriority = null
                }
            )
        }
    }
}
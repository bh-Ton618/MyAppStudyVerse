package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CoPresent
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myappstudyverse.R
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.LectureRepository
import com.example.myappstudyverse.data.local.TaskRepository
import com.example.myappstudyverse.ui.theme.Gold
import com.example.myappstudyverse.ui.theme.Green
import com.example.myappstudyverse.ui.theme.Gridline
import com.example.myappstudyverse.ui.theme.Gridline2
import com.example.myappstudyverse.ui.theme.NavigationSurface
import com.example.myappstudyverse.ui.theme.OffWhite1
import com.example.myappstudyverse.ui.theme.OffWhite2
import com.example.myappstudyverse.ui.theme.PurplePrimary
import com.example.myappstudyverse.ui.theme.PurpleSecondary
import com.example.myappstudyverse.ui.theme.Rose
import com.example.myappstudyverse.ui.theme.SpaceSurface
import com.example.myappstudyverse.ui.theme.TextSecondary
import com.example.myappstudyverse.ui.viewmodel.LectureViewModel
import com.example.myappstudyverse.ui.viewmodel.LectureViewModelFactory
import com.example.myappstudyverse.ui.viewmodel.TaskViewModel
import com.example.myappstudyverse.ui.viewmodel.TaskViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// Main dashboard screen providing quick access to app's core features.
@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    val context = androidx.compose.ui.platform.LocalContext.current

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

    val tasks by taskViewModel.tasks.collectAsState()
    val lectures by lectureViewModel.lectures.collectAsState()



    Scaffold(
        // Floating action button reserved for creating future content (currently placeholder)
        floatingActionButton = {

            Box(contentAlignment = Alignment.BottomEnd)
            {
                FloatingActionButton(
                    onClick = {
                        isFabExpanded = !isFabExpanded
                    },
                    modifier = Modifier
                        .offset(y = 28.dp)
                        .alpha(if (isFabExpanded) 0f else 1f),
                    shape = CircleShape,
                    containerColor = PurplePrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = OffWhite1
                    )
                }

                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        modifier = Modifier.offset(y = 20.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isFabExpanded) {
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(NavigationSurface)
                                    .border(
                                        width = 1.dp,
                                        color = PurplePrimary.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clickable {
                                        isFabExpanded = false
                                        navController.navigate("taskDetail/new?type=${TaskType.TASK}")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckBox,
                                    contentDescription = null,
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "New Task",
                                    fontWeight = FontWeight.Medium,
                                    color = OffWhite2
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(NavigationSurface)
                                    .border(
                                        width = 1.dp,
                                        color = PurplePrimary.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clickable {
                                        isFabExpanded = false
                                        navController.navigate("lectureDetail/new")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CoPresent,
                                    contentDescription = null,
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "New Lecture",
                                    fontWeight = FontWeight.Medium,
                                    color = OffWhite2
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(NavigationSurface)
                                    .border(
                                        width = 1.dp,
                                        color = PurplePrimary.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clickable {
                                        isFabExpanded = false
                                        navController.navigate("taskDetail/new?type=${TaskType.EXAM}")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.EditNote,
                                    contentDescription = null,
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "New Exam",
                                    fontWeight = FontWeight.Medium,
                                    color = OffWhite2
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(NavigationSurface)
                                    .border(
                                        width = 1.dp,
                                        color = PurplePrimary.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clickable {
                                        isFabExpanded = false
                                        navController.navigate("noteDetail/new")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.EditNote,
                                    contentDescription = null,
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "New Note",
                                    fontWeight = FontWeight.Medium,
                                    color = OffWhite2
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = modifier
                    .statusBarsPadding()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 24.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            )
            {
                GreetingSection()
                Spacer(modifier = Modifier.height(16.dp))

                MotivationSection()
                Spacer(modifier = Modifier.height(24.dp))

                TodayOverviewSection(tasks = tasks, lectures = lectures)
                Spacer(modifier = Modifier.height(16.dp))

                UpComingSection(tasks = tasks, lectures = lectures)
                Spacer(modifier = Modifier.height(16.dp))
            }

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


// Displays a personalized greeting and profile shortcut.
@Composable
fun GreetingSection() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val username = currentUser?.displayName?.ifBlank { "Space Explorer" } ?: "Space Explorer"

    Column {
        Row {
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "Welcome back, $username \uD83D\uDC4B", color = OffWhite2
                )

                val currentDate = LocalDate.now()
                val formattedDate =
                    currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))

                Text(
                    text = formattedDate, color = OffWhite2
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(indication = null,
                       interactionSource = remember { MutableInteractionSource()}
                    ) {
                        // TODO: Navigate to profile.
                    }
                    .background(color = NavigationSurface)
                    .border(width = 1.dp, color = Gridline2, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = username.first().uppercase(),
                    color = OffWhite2
                )
            }
        }
    }
}


// Displays a daily motivational quote to encourage the user.
@Composable
fun MotivationSection(modifier: Modifier = Modifier) {

    val motivationalQuotes = listOf(
        "Shoot for the moon. Even if you miss, you'll land among the stars.",
        "Every great journey begins with a single step into the unknown.",
        "The stars remind us that even the darkest nights can shine.",
        "Aim beyond the horizon—there's always another galaxy to explore.",
        "Dream big enough to reach the stars.",
        "Every star was once a cloud of dust with potential.",
        "The universe rewards those who never stop exploring.",
        "Your future is written among the stars you choose to follow.",
        "Keep looking up. The sky is never the limit.",
        "Great discoveries begin with the courage to launch."
    )

    val currentDay = LocalDate.now().dayOfYear
    val dailyQuote = motivationalQuotes[currentDay % motivationalQuotes.size]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpaceSurface
        ),
        border = BorderStroke(1.dp, Gridline)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.motivation_wallpaper2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .width(235.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Daily Motivation",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dailyQuote,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = OffWhite2
                    )
                }
            }
        }
    }
}


// Displays a summary of today's tasks, classes and exams.
@Composable
fun TodayOverviewSection(
    tasks: List<Task>,
    lectures: List<Lecture>
) {

    val today = LocalDate.now()

    val todayDate = today.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )

    val todayDay = when (today.dayOfWeek) {
        java.time.DayOfWeek.MONDAY -> DayOfWeek.MONDAY
        java.time.DayOfWeek.TUESDAY -> DayOfWeek.TUESDAY
        java.time.DayOfWeek.WEDNESDAY -> DayOfWeek.WEDNESDAY
        java.time.DayOfWeek.THURSDAY -> DayOfWeek.THURSDAY
        java.time.DayOfWeek.FRIDAY -> DayOfWeek.FRIDAY
        else -> null
    }

    val todayTasks = tasks.count { task ->
        task.type == TaskType.TASK &&
                task.dueDate == todayDate
    }

    val todayExams = tasks.count { task ->
        task.type == TaskType.EXAM &&
                task.dueDate == todayDate
    }

    val todayLectures = lectures.count { lecture ->
        lecture.day == todayDay
    }

    Column {
        Text(
            text = "Today's Overview",
            color = OffWhite2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.CheckBox,
                number = todayTasks.toString(),
                title = if (todayTasks == 1) "Task" else "Tasks"
            )

            Spacer(modifier = Modifier.width(8.dp))

            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.CoPresent,
                number = todayLectures.toString(),
                title = if (todayLectures == 1) "Lecture" else "Lectures"
            )

            Spacer(modifier = Modifier.width(8.dp))

            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.School,
                number = todayExams.toString(),
                title = if (todayExams == 1) "Exam" else "Exams"
            )
        }
    }
}


// Reusable card component for today's overview statistics.
@Composable
fun OverViewCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    number: String,
    title: String
) {
    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpaceSurface
        ),
        border = BorderStroke(1.dp, Gridline)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PurplePrimary.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PurpleSecondary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = number, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OffWhite2)

            Spacer(modifier = Modifier.height(2.dp))

            Text(text = title, color = OffWhite2)
        }
    }
}


// Displays upcoming academic events and deadlines.
@Composable
fun UpComingSection(
    tasks: List<Task>,
    lectures: List<Lecture>
) {
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Finds the next task with a valid future due date.
    val nextTask = tasks
        .mapNotNull { task ->
            try {
                val date = LocalDate.parse(task.dueDate, dateFormatter)

                if (!date.isBefore(today)) {
                    task to date
                } else {
                    null
                }
            } catch (e: DateTimeParseException) {
                null
            }
        }
        .minByOrNull { (_, date) -> date }

    // Finds the next exam with a valid future exam date.
    val nextExam = tasks
        .filter { task -> task.type == TaskType.EXAM }
        .mapNotNull { exam ->
            try {
                val date = LocalDate.parse(exam.dueDate, dateFormatter)

                if (!date.isBefore(today)) {
                    exam to date
                } else {
                    null
                }
            } catch (e: DateTimeParseException) {
                null
            }
        }
        .minByOrNull { (_, date) -> date }

    // Finds the next weekday with a lecture.
    val nextLecture = (0..6)
        .map { daysFromToday ->
            val date = today.plusDays(daysFromToday.toLong())

            val day = when (date.dayOfWeek) {
                java.time.DayOfWeek.MONDAY -> DayOfWeek.MONDAY
                java.time.DayOfWeek.TUESDAY -> DayOfWeek.TUESDAY
                java.time.DayOfWeek.WEDNESDAY -> DayOfWeek.WEDNESDAY
                java.time.DayOfWeek.THURSDAY -> DayOfWeek.THURSDAY
                java.time.DayOfWeek.FRIDAY -> DayOfWeek.FRIDAY
                else -> null
            }

            date to day
        }
        .firstNotNullOfOrNull { (date, day) ->
            if (day == null) {
                null
            } else {
                lectures
                    .filter { lecture -> lecture.day == day }
                    .minByOrNull { lecture -> lecture.startTime }
                    ?.let { lecture -> lecture to date }
            }
        }

    Column {
        Text(
            text = "Upcoming",
            color = OffWhite2,
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = nextTask?.first?.title ?: "No missions ✨",
            subtitle = nextTask?.first?.dueDate ?: "Your mission log is clear.",
            icon = Icons.Outlined.CheckBox,
            color = Rose
        )

        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = nextLecture?.first?.title ?: "No Lectures today. ✨",
            subtitle = nextLecture?.second?.format(dateFormatter)
                ?: "Enjoy the empty space",
            icon = Icons.Outlined.CoPresent,
            color = Green
        )

        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = nextExam?.first?.title ?: "Exam free zone ✨",
            subtitle = nextExam?.first?.dueDate ?: "No tests on the radar.",
            icon = Icons.Outlined.School,
            color = Gold
        )
    }
}


// Reusable card displaying an upcoming activity or event.
@Composable
fun UpcomingCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier,
        border = BorderStroke(1.dp, Gridline),
        colors = CardDefaults.cardColors(
            containerColor = SpaceSurface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}
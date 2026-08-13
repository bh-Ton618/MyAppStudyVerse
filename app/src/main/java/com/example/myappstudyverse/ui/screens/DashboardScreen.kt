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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CoPresent
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter


//Main dashboard screen providing quick access to app's core features.
@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var isFabExpanded by remember { mutableStateOf(false) }

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
                    containerColor = Color(0xFFA78BFA)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
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
                                    .background(Color.White)
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFA78BFA),
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
                                        navController.navigate("lectureDetail/new")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CoPresent,
                                    contentDescription = null,
                                    tint = Color(0xFFA78BFA),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "New Lecture",
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
                                        navController.navigate("taskDetail/new?type=${TaskType.EXAM}")
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
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

                TodayOverviewSection()
                Spacer(modifier = Modifier.height(16.dp))

                UpComingSection()
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
                    text = "Welcome back, $username \uD83D\uDC4B"
                )
                val currentDate = LocalDate.now()
                val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
                Text(
                    text = formattedDate
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        //TODO: Navigate to profile.
                    }
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center) {
                Text(username.first().uppercase())
            }

        }
    }
}


// Displays a daily motivational quote to encourage the user.
@Composable
fun MotivationSection(modifier: Modifier = Modifier) {

    // TODO: Replace random selection with a daily quote based on the current date.
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
    val dailyQuote = remember { motivationalQuotes.random() }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Daily Motivation",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA78BFA)
                )


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = dailyQuote,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                    //color = Color.White
                )

            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                RocketIcon()
            }
        }
    }
}

@Composable
fun RocketIcon() {
    Text(text = "\uD83D\uDE80", fontSize = 40.sp)
}

// Displays a summary of today's tasks, classes and exams.
@Composable
fun TodayOverviewSection() {
    Column {
        Text(text = "Today's Overview")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = "\uD83D\uDCCB",
                number = "12",
                title = "Task"
            )
            Spacer(modifier = Modifier.width(8.dp))
            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = "\uD83D\uDCDA",
                number = "2",
                title = "Lecture"
            )
            Spacer(modifier = Modifier.width(8.dp))
            OverViewCard(
                modifier = Modifier.weight(1f),
                icon = "\uD83D\uDCDD",
                number = "1",
                title = "Exam"
            )
        }
    }

}

// Reusable card component for today's overview statistics.
@Composable
fun OverViewCard(modifier: Modifier = Modifier, icon: String, number: String, title: String) {

    Card(modifier = modifier.height(120.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = number)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title)

        }
    }
}

// Displays upcoming academic events and deadlines.
@Composable
fun UpComingSection() {
    Column {
        Text("Upcoming")
        Spacer(modifier = Modifier.height(8.dp))
        UpcomingCard(
            title = "Math Homework",
            subtitle = "Due Tomorrow 2PM",
            icon = "\uD83D\uDCD8"
        )
        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = "Physics Class",
            subtitle = "Tomorrow 4PM",
            icon = "\uD83E\uDDEA"
        )
        Spacer(modifier = Modifier.height(8.dp))

        UpcomingCard(
            title = "Business Communication Exam",
            subtitle = "Tomorrow 8AM ",
            icon = "\uD83D\uDCDD"
        )

    }
}

// Reusable card displaying an upcoming activity or event.
@Composable
fun UpcomingCard(title: String, subtitle: String, icon: String) {
    Card {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
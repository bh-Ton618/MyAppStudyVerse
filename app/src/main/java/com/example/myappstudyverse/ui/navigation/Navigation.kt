package com.example.myappstudyverse.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myappstudyverse.ui.screens.AuthScreen
import com.example.myappstudyverse.ui.screens.DashboardScreen
import com.example.myappstudyverse.ui.screens.LectureDetailScreen
import com.example.myappstudyverse.ui.screens.LectureScreen
import com.example.myappstudyverse.ui.screens.NoteDetailScreen
import com.example.myappstudyverse.ui.screens.NoteScreen
import com.example.myappstudyverse.ui.screens.SpaceScreen
import com.example.myappstudyverse.ui.screens.TaskDetailScreen
import com.example.myappstudyverse.ui.screens.TaskType
import com.example.myappstudyverse.ui.screens.TasksScreen


// Configures the application's navigation graph and screen routing.
@Composable
fun StudyVerseNavigation() {

    // Creates and remembers the navigation controller used throughout the app.
    val navController: NavHostController = rememberNavController()
    // Determines the currently displayed screen.
    val currentNavigationState = navController.currentBackStackEntryAsState()
    val currentScreen = currentNavigationState.value?.destination?.route



    Scaffold(
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0),
        bottomBar = {
            // Hides the bottom navigation on full screen pages.
            if (currentScreen != "space" && currentScreen != "auth" && currentScreen?.startsWith("noteDetail/") != true && currentScreen?.startsWith(
                    "taskDetail/"
                ) != true && currentScreen?.startsWith("lectureDetail/") != true
            ) {
                BottomNavigationBar(navController)
            }
        }) { innerPadding ->

        // Defines all navigation destinations within the application.
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "auth"
        ) {
            composable("auth") {

                AuthScreen(navController)
            }
            composable("dashboard") {
                DashboardScreen(navController)
            }
            composable("tasks") {
                TasksScreen(navController)
            }
            composable("lectures") {
                LectureScreen(navController)
            }

            composable(route = "lectureDetail/{lectureId}") { backStackEntry ->
                val lectureId = backStackEntry.arguments?.getString("lectureId")?.toIntOrNull()
                LectureDetailScreen(navController = navController, lectureId = lectureId)
            }
            composable(route = "lectureDetail/new") {
                LectureDetailScreen(navController = navController, lectureId = null)
            }

            composable("notes") {
                NoteScreen(navController)
            }

            composable(route = "noteDetail/new") {
                NoteDetailScreen(navController = navController, noteId = null)
            }

            composable(route = "noteDetail/{noteId}") { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                NoteDetailScreen(
                    navController = navController,
                    noteId = noteId
                )
            }

            composable(route = "taskDetail/{taskId}?type={type}") { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                val taskType = backStackEntry.arguments?.getString("type")
                TaskDetailScreen(
                    navController = navController,
                    taskId = taskId,
                    taskType = if (taskType == "EXAM") TaskType.EXAM else TaskType.TASK
                )
            }

            composable(route = "taskDetail/new?type={type}") { backStackEntry ->
                val taskType = backStackEntry.arguments?.getString("type")
                TaskDetailScreen(
                    navController = navController,
                    taskId = null,
                    taskType = if (taskType == "EXAM") TaskType.EXAM else TaskType.TASK
                )
            }

            composable("space") {
                SpaceScreen(navController)
            }
        }
    }
}


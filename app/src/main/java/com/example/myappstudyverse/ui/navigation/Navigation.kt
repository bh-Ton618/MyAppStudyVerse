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
import com.example.myappstudyverse.ui.screens.NotesScreen
import com.example.myappstudyverse.ui.screens.SpaceScreen
import com.example.myappstudyverse.ui.screens.TasksScreen
import com.example.myappstudyverse.ui.screens.TimetableScreen


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
            if (currentScreen != "space" && currentScreen != "auth") {
                BottomNavigationBar(navController)
            }
        }) { innerPadding ->

        // Defines all navigation destinations within the application.
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "dashboard"
        ) {
            composable("auth") {

                AuthScreen(navController)
            }
            composable("dashboard") {
                DashboardScreen(navController)
            }
            composable("tasks") {
                TasksScreen()
            }
            composable("timetable") {
                TimetableScreen()
            }
            composable("notes") {
                NotesScreen()
            }
            composable("space") {
                SpaceScreen(navController)
            }
        }
    }
}


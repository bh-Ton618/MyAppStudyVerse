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
import com.example.myappstudyverse.ui.screens.DashboardScreen
import com.example.myappstudyverse.ui.screens.NotesScreen
import com.example.myappstudyverse.ui.screens.SpaceScreen
import com.example.myappstudyverse.ui.screens.TasksScreen
import com.example.myappstudyverse.ui.screens.TimetableScreen


@Composable
fun StudyVerseNavigation() {

    val navController: NavHostController = rememberNavController()
    val currentNavigationState = navController.currentBackStackEntryAsState()
    val currentScreen = currentNavigationState.value?.destination?.route



    Scaffold(
        bottomBar = {
            if (currentScreen != "space") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "dashboard"
        ) {
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


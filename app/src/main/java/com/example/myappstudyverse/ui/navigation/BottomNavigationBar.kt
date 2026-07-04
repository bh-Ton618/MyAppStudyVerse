package com.example.myappstudyverse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {

//Get the currently displayed screen to highlight the selected navigation icon
    val currentNavigationState = navController.currentBackStackEntryAsState()
    val currentScreen = currentNavigationState.value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == "dashbaord",
            onClick = {
                navController.navigate("dashbaord")
            },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == "tasks",
            onClick = {
                navController.navigate("tasks")
            },
            icon = {
                Icon(
                    Icons.Outlined.AssignmentTurnedIn,
                    contentDescription = "Tasks"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == "timetable",
            onClick = {
                navController.navigate("timetable")
            },
            icon = {
                Icon(
                    Icons.Outlined.CalendarMonth,
                    contentDescription = "Timetable"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == "notes",
            onClick = {
                navController.navigate("notes")
            },
            icon = {
                Icon(
                    Icons.Outlined.NoteAlt,
                    contentDescription = "Notes"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == "space",
            onClick = {
                navController.navigate("space")
            },
            icon = {
                Icon(
                    Icons.Outlined.AutoAwesome,
                    contentDescription = "Space"
                )
            }
        )
    }
}

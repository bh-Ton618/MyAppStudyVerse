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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myappstudyverse.ui.theme.PurplePrimary
import com.example.myappstudyverse.ui.theme.SpaceBackground
import com.example.myappstudyverse.ui.theme.TextSecondary


// Displays the bottom navigation bar for switching between the main application screens.
@Composable
fun BottomNavigationBar(navController: NavController) {

    // Retrieves the current navigation route to highlight the selected navigation item.
    val currentNavigationState = navController.currentBackStackEntryAsState()
    val currentScreen = currentNavigationState.value?.destination?.route

    // Provides navigation between the app's primary screens.
    NavigationBar(
        containerColor = SpaceBackground,
        tonalElevation = 0.dp
    ) {

        NavigationBarItem(
            selected = currentScreen == "dashboard",
            onClick = {
                navController.navigate("dashboard")
            },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurplePrimary,
                unselectedIconColor = TextSecondary,
                indicatorColor = SpaceBackground
            )
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
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurplePrimary,
                unselectedIconColor = TextSecondary,
                indicatorColor = SpaceBackground
            )
        )

        NavigationBarItem(
            selected = currentScreen == "lectures",
            onClick = {
                navController.navigate("lectures")
            },
            icon = {
                Icon(
                    Icons.Outlined.CalendarMonth,
                    contentDescription = "Lectures"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurplePrimary,
                unselectedIconColor = TextSecondary,
                indicatorColor = SpaceBackground
            )
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
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurplePrimary,
                unselectedIconColor = TextSecondary,
                indicatorColor = SpaceBackground
            )
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
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurplePrimary,
                unselectedIconColor = TextSecondary,
                indicatorColor = SpaceBackground
            )
        )
    }
}
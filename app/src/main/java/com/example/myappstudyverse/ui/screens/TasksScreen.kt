package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun TasksScreen() {
    var selectedFilterChip by remember { mutableStateOf("All") }
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 24.dp)) {
        Text(
            text = "My Tasks",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            TaskFilterChip(
                text = "All",
                isSelected = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskFilterChip(
                text = "to Do",
                isSelected = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskFilterChip(
                text = "Done",
                isSelected = false
            )
        }


    }
}


@Composable
fun TaskFilterChip(
    text: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .size(width = 115.dp, height = 40.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(
                if (isSelected) Color.Gray
                else Color.LightGray
            )
            .clickable {
                // TODO
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

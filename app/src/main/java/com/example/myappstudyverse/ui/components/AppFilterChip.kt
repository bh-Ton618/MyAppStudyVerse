package com.example.myappstudyverse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappstudyverse.ui.theme.FilterChipSurface
import com.example.myappstudyverse.ui.theme.PurplePrimary
import com.example.myappstudyverse.ui.theme.TextPrimary
import com.example.myappstudyverse.ui.theme.TextSecondary

// Reusable filter chip used for selecting filter and view options.
@Composable
fun AppFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                width = 115.dp,
                height = 40.dp
            )
            .background(
                color = if (isSelected) {
                    PurplePrimary
                } else {
                    FilterChipSurface
                },
                shape = RoundedCornerShape(50.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) {
                TextPrimary
            } else {
                TextSecondary
            }
        )
    }
}
package com.example.myappstudyverse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myappstudyverse.ui.screens.DayOfWeek

@Entity(tableName = "lectures")
data class LectureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val room: String,
    val day: DayOfWeek,
    val startTime: String,
    val endTime: String,
    val lecturer: String?
)


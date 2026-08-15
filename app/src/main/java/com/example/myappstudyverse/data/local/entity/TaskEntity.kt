package com.example.myappstudyverse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myappstudyverse.ui.screens.ExamType
import com.example.myappstudyverse.ui.screens.Priority
import com.example.myappstudyverse.ui.screens.TaskType

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val dueDate: String?,
    val description: String?,
    val priority: Priority?,
    val isDone: Boolean,
    val type: TaskType,
    val createdAt: Long,
    val professor: String?,
    val examType: ExamType?
    )

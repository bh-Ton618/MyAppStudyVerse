package com.example.myappstudyverse.data.local.converter

import androidx.room.TypeConverter
import com.example.myappstudyverse.ui.screens.ExamType
import com.example.myappstudyverse.ui.screens.Priority
import com.example.myappstudyverse.ui.screens.TaskType



class TaskConverters {

    @TypeConverter
    fun fromPriority(priority: Priority?): String? =
        priority?.name

    @TypeConverter
    fun toPriority(value: String?): Priority? =
        value?.let { priorityName ->
            Priority.valueOf(priorityName)
        }

    @TypeConverter
    fun fromTaskType(type: TaskType): String =
        type.name

    @TypeConverter
    fun toTaskType(value: String): TaskType =
        TaskType.valueOf(value)

    @TypeConverter
    fun fromExamType(type: ExamType?): String? =
        type?.name

    @TypeConverter
    fun toExamType(value: String?): ExamType? =
        value?.let { examTypeName ->
            ExamType.valueOf(examTypeName)
        }
}
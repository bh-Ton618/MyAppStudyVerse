package com.example.myappstudyverse.data.local.converter

import androidx.room.TypeConverter
import com.example.myappstudyverse.ui.screens.DayOfWeek

class LectureConverters {

    @TypeConverter
    fun fromDayOfWeek(day: DayOfWeek): String {
        return day.name
    }

    @TypeConverter
    fun toDayOfWeek(day: String): DayOfWeek {
        return DayOfWeek.valueOf(day)
    }
}
package com.example.myappstudyverse.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myappstudyverse.data.local.converter.LectureConverters
import com.example.myappstudyverse.data.local.converter.TaskConverters
import com.example.myappstudyverse.data.local.entity.LectureEntity
import com.example.myappstudyverse.data.local.entity.NoteEntity
import com.example.myappstudyverse.data.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        LectureEntity::class,
        NoteEntity::class
    ],

    version = 3
)
@TypeConverters(
    TaskConverters::class,
    LectureConverters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun lectureDao(): LectureDao
    abstract fun noteDao(): NoteDao
}


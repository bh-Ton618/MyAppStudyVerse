package com.example.myappstudyverse.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myappstudyverse.data.local.entity.LectureEntity

@Dao
interface LectureDao {

    @Query("SELECT * FROM lectures")
    suspend fun getAllLectures(): List<LectureEntity>

    @Insert
    suspend fun insertLecture(lecture: LectureEntity)

    @Update
    suspend fun updateLecture(lecture: LectureEntity)

    @Delete
    suspend fun deleteLecture(lecture: LectureEntity)
}
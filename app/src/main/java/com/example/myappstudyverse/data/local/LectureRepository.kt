package com.example.myappstudyverse.data.local

import com.example.myappstudyverse.data.local.entity.LectureEntity
import com.example.myappstudyverse.ui.screens.Lecture


class LectureRepository(
    private val lectureDao: LectureDao
) {

    suspend fun getAllLectures(): List<Lecture> {
        return lectureDao.getAllLectures().map { lectureEntity ->
            lectureEntity.toLecture()
        }
    }

    suspend fun insertLecture(lecture: Lecture) {
        lectureDao.insertLecture(lecture.toEntity(id = 0))
    }

    suspend fun updateLecture(lecture: Lecture) {
        lectureDao.updateLecture(lecture.toEntity())
    }

    suspend fun deleteLecture(lecture: Lecture) {
        lectureDao.deleteLecture(lecture.toEntity())
    }


    private fun Lecture.toEntity(id: Int = this.id): LectureEntity {
        return LectureEntity(
            id = id,
            title = title,
            room = room,
            day = day,
            startTime = startTime,
            endTime = endTime,
            lecturer = lecturer
        )
    }

    private fun LectureEntity.toLecture(): Lecture {
        return Lecture(
            id = id,
            title = title,
            room = room,
            day = day,
            startTime = startTime,
            endTime = endTime,
            lecturer = lecturer
        )
    }
}
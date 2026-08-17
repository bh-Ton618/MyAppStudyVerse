package com.example.myappstudyverse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myappstudyverse.data.local.LectureRepository
import com.example.myappstudyverse.ui.screens.Lecture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LectureViewModel(
    private val repository: LectureRepository
) : ViewModel() {

    private val _lectures = MutableStateFlow<List<Lecture>>(emptyList())
    val lectures: StateFlow<List<Lecture>> = _lectures

    // Loads lectures when the ViewModel is created.
    init {
        loadLectures()
    }

    fun loadLectures() {
        viewModelScope.launch {
            _lectures.value = repository.getAllLectures()
        }
    }

    fun addLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.insertLecture(lecture)
            loadLectures()
        }
    }

    fun updateLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.updateLecture(lecture)
            loadLectures()
        }
    }

    fun deleteLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.deleteLecture(lecture)
            loadLectures()
        }
    }
}


class LectureViewModelFactory(
    private val repository: LectureRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LectureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LectureViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
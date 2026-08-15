package com.example.myappstudyverse.data.local

import com.example.myappstudyverse.data.local.entity.NoteEntity
import com.example.myappstudyverse.ui.screens.Note

class NoteRepository(
    private val noteDao: NoteDao
) {

    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes().map { noteEntity ->
            noteEntity.toNote()
        }
    }

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toEntity(id = 0))
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    private fun NoteEntity.toNote(): Note {
        return Note(
            id = id,
            title = title,
            createdDate = createdDate,
            description = description,
            isPinned = isPinned
        )
    }

    private fun Note.toEntity(id: Int = this.id): NoteEntity {
        return NoteEntity(
            id = id,
            title = title,
            createdDate = createdDate,
            description = description,
            isPinned = isPinned
        )
    }
}
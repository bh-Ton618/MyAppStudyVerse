package com.example.myappstudyverse.data.local

import com.example.myappstudyverse.data.local.entity.TaskEntity
import com.example.myappstudyverse.ui.screens.Task



class TaskRepository(
    private val taskDao: TaskDao
) {
    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { taskEntity ->
            taskEntity.toTask()
        }
    }
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity(id = 0))
    }
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }


    private fun Task.toEntity(id: Int = this.id): TaskEntity {
        return TaskEntity(
            id = id,
            title = title,
            dueDate = dueDate.takeIf { dueDateText ->
                dueDateText.isNotBlank()
            },
            description = description.takeIf { descriptionText ->
                descriptionText.isNotBlank()
            },
            priority = priority,
            isDone = isDone,
            type = type,
            createdAt = createdAt,
            professor = professor,
            examType = examType
        )
    }

    private fun TaskEntity.toTask(): Task {
        return Task(
            id = id,
            title = title,
            dueDate = dueDate ?: " ",
            description = description ?: " ",
            priority = priority,
            isDone = isDone,
            type = type,
            createdAt = createdAt,
            professor = professor,
            examType = examType
        )
    }
}
package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.Task

interface TaskRepository {
    suspend fun setTask(task: Task): Void?
    suspend fun getTasks(): List<Task>
    suspend fun getTasksByTitle(title: String): List<Task>
    suspend fun getTaskOrderBy(order: Boolean): List<Task>
    suspend fun updateTask(task: Task): Void?
    suspend fun isTaskDone(task: Task): Void?
    suspend fun deleteTaskByID(taskId: String): Void?
}
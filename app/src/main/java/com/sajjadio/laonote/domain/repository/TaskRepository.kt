package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.Task

interface TaskRepository {
    suspend fun setTask(task: Task): Void?
    suspend fun getTasks(userID: String): List<Task>
    suspend fun searchAboutTask(task: Task): List<Task>
    suspend fun getTaskOrder(task: Task): List<Task>
    suspend fun updateTask(task: Task): Void?
    suspend fun isTaskDone(task: Task): Void?
    suspend fun deleteTask(task: Task): Void?
}
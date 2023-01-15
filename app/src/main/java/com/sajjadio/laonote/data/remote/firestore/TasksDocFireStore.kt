package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Task

interface TasksDocFireStore {
    // tasks collection
    suspend fun setTask(task: Task): Void?
    suspend fun getTasks(userID: String): List<Task>
    suspend fun searchAboutTask(task: Task): List<Task>
    suspend fun getTaskOrder(task: Task): List<Task>
    suspend fun updateTask(task: Task): Void?
    suspend fun isTaskDone(task: Task): Void?
    suspend fun deleteTask(task: Task): Void?
}
package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.Task
interface TaskRepository {
    suspend fun setTask(data: HashMap<String, Any?>): Void?
    suspend fun getTasks(): List<Task>
    suspend fun updateTaskByID(data: HashMap<String, Any?>): Void?
    suspend fun isTaskDone(data: HashMap<String, Any?>): Void?
    suspend fun deleteTaskByID(taskId: String): Void?
}
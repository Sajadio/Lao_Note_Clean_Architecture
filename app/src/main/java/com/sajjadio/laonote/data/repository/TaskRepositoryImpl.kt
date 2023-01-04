package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val docFireStore: DocumentaryFireStore,
) : TaskRepository {

    override suspend fun setTask(data: HashMap<String, Any?>) = docFireStore.setTask(data)

    override suspend fun getTasks() = docFireStore.getTasks()

    override suspend fun updateTaskByID(data: HashMap<String, Any?>) = docFireStore.updateTaskByID(data)

    override suspend fun deleteTaskByID(taskId: String) = docFireStore.deleteTaskByID(taskId)
}
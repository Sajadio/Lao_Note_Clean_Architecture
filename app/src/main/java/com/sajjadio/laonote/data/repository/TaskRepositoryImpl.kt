package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.TasksDocFireStore
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDocFireStore: TasksDocFireStore
) : TaskRepository {

    override suspend fun setTask(task: Task) =
        tasksDocFireStore.setTask(task)

    override suspend fun getTasks(userID: String) =
        tasksDocFireStore.getTasks(userID)

    override suspend fun searchAboutTask(task: Task) =
        tasksDocFireStore.searchAboutTask(task)

    override suspend fun getTaskOrder(task: Task) =
        tasksDocFireStore.getTaskOrder(task)

    override suspend fun updateTask(task: Task) =
        tasksDocFireStore.updateTask(task)

    override suspend fun isTaskDone(task: Task): Void? =
        tasksDocFireStore.isTaskDone(task)

    override suspend fun deleteTask(task: Task) =
        tasksDocFireStore.deleteTask(task)
}
package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.TasksDocFireStore
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDocFireStore: TasksDocFireStore
) : TaskRepository {

    override suspend fun setTask(task: Task) = tasksDocFireStore.setTask(task)

    override suspend fun getTasks() = tasksDocFireStore.getTasks()

    override suspend fun getTasksByTitle(title: String): List<Task> =
        tasksDocFireStore.getTasksByTitle(title)

    override suspend fun getTaskOrderBy(order: Boolean): List<Task>  =
        tasksDocFireStore.getTaskOrderBy(order)

    override suspend fun updateTask(task: Task) = tasksDocFireStore.updateTask(task)

    override suspend fun isTaskDone(task: Task): Void? =
        tasksDocFireStore.isTaskDone(task)

    override suspend fun deleteTaskByID(taskId: String) = tasksDocFireStore.deleteTaskByID(taskId)
}
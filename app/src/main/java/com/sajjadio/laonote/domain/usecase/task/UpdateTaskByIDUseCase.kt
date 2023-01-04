package com.sajjadio.laonote.domain.usecase.task

import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.HashMap

class UpdateTaskByIDUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = flow {
        try {
            emit(NetworkResponse.Loading)
            val updateTask = HashMap<String, Any?>()
            updateTask[TASK_ID] = task.task_id
            updateTask[TASK_TITLE] = task.task_title
            updateTask[TASK_DESCRIPTION] = task.task_description
            updateTask[TASK_WEB_URL] = task.task_webUrl
            updateTask[TASK_DATE_CREATED] = task.task_date_created
            val response = taskRepository.updateTaskByID(updateTask)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
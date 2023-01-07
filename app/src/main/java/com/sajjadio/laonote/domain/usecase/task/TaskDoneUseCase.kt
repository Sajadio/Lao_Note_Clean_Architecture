package com.sajjadio.laonote.domain.usecase.task

import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskDoneUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = flow {
        try {
            emit(NetworkResponse.Loading)
            val updateTask = HashMap<String, Any?>()
            updateTask[TASK_ID] = task.task_id
            updateTask[IS_DONE] = task.is_done
            val response = taskRepository.isTaskDone(updateTask)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))

        }
    }

}
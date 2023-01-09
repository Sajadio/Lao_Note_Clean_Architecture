package com.sajjadio.laonote.domain.usecase.task

import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTasksByTitleUseCase @Inject constructor(
    private val taskRepo: TaskRepository
) {
    operator fun invoke(title: String) = flow {
        try {
            val tasks = mutableListOf<Task>()
            emit(NetworkResponse.Loading)
            val response = taskRepo.getTasks()
            response.forEach { result ->
                if (
                    (result.task_title?.startsWith(title, true) == true) ||
                    result.task_title?.endsWith(title, true) == true
                ) {
                    tasks.add(result)
                    emit(NetworkResponse.Success(tasks.toList()))
                } else
                    emit(NetworkResponse.Success(tasks.toList()))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
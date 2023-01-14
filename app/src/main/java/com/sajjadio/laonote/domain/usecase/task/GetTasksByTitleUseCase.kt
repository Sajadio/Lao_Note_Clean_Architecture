package com.sajjadio.laonote.domain.usecase.task

import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTasksByTitleUseCase @Inject constructor(
    private val taskRepo: TaskRepository
) {
    operator fun invoke(title: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = taskRepo.getTasksByTitle(title)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
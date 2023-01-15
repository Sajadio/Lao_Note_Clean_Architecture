package com.sajjadio.laonote.domain.usecase.task

import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepo: TaskRepository
) {
    operator fun invoke(userID:String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = taskRepo.getTasks(userID)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
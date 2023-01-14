package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsByDateUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(date: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = eventRepository.getTasksByDate(date)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
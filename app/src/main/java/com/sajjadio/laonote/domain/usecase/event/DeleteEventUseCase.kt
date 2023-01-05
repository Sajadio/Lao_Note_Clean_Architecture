package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.domain.repository.TaskRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = eventRepository.deleteEventByID(eventId)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
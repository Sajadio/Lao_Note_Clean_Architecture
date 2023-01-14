package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(event: EventModel) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = eventRepository.updateEvent(event)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.HashMap

class UpdateEventByIDUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventModel: EventModel) = flow {
        try {
            emit(NetworkResponse.Loading)
            val updateEvent = HashMap<String, Any?>()
            updateEvent[EVENT_ID] = eventModel.event_id
            updateEvent[EVENT_TITLE] = eventModel.event_title
            updateEvent[EVENT_DESCRIPTION] = eventModel.event_description
            updateEvent[EVENT_DATE_CREATED] = eventModel.event_date_created
            val response = eventRepository.updateEventByID(updateEvent)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
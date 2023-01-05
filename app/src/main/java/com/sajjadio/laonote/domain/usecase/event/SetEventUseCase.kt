package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.HashMap

class SetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventModel: EventModel) = flow {
        try {
            emit(NetworkResponse.Loading)
            val setEvent = HashMap<String, Any?>()
            setEvent[EVENT_ID] = eventModel.event_id
            setEvent[EVENT_TITLE] = eventModel.event_title
            setEvent[EVENT_DESCRIPTION] = eventModel.event_description
            setEvent[EVENT_DATE_CREATED] = eventModel.event_date_created
            val response = eventRepository.setEvent(setEvent)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
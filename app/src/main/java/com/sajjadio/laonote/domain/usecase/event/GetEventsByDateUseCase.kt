package com.sajjadio.laonote.domain.usecase.event

import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.utils.FULL_DATE
import com.sajjadio.laonote.utils.MONTH_DAY_YEAR
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.extension.formatDate
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsByDateUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(date: String) = flow {
        try {
            val events = mutableListOf<EventModel>()
            emit(NetworkResponse.Loading)
            val response = eventRepository.getEvents()
            response.forEach { result ->
                if (compareBetweenDates(result.event_date_created, date)) {
                    events.add(result)
                    emit(NetworkResponse.Success(events.toList()))
                } else
                    emit(NetworkResponse.Success(events.toList()))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }

    private fun compareBetweenDates(eventDateCreated: String?, date: String): Boolean {
        val firstDate = eventDateCreated.formatDate(FULL_DATE, MONTH_DAY_YEAR)
        return firstDate.equals(date)
    }
}
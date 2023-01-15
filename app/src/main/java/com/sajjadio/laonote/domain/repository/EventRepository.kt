package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.EventModel

interface EventRepository {
    suspend fun setEvent(event: EventModel): Void?
    suspend fun getEvents(userID: String): List<EventModel>
    suspend fun getTasksByDate(event: EventModel): List<EventModel>
    suspend fun updateEvent(event: EventModel): Void?
    suspend fun deleteEvent(event: EventModel): Void?
}
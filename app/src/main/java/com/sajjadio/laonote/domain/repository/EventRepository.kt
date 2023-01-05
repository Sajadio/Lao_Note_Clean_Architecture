package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.EventModel

interface EventRepository {
    suspend fun setEvent(data: HashMap<String, Any?>): Void?
    suspend fun getEvents(): List<EventModel>
    suspend fun updateEventByID(data: HashMap<String, Any?>): Void?
    suspend fun deleteEventByID(eventId: String): Void?
}
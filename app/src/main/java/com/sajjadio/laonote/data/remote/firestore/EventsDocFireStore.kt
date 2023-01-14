package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.EventModel

interface EventsDocFireStore {
    // events collection
    suspend fun setEvent(event: EventModel): Void?
    suspend fun getEvents(): List<EventModel>
    suspend fun getTasksByDate(date: String): List<EventModel>
    suspend fun updateEvent(event: EventModel): Void?
    suspend fun deleteEventByID(eventId: String): Void?
}
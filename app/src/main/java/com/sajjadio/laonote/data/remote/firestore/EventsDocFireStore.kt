package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.EventModel

interface EventsDocFireStore {
    // events collection
    suspend fun setEvent(event: EventModel): Void?
    suspend fun getEvents(userID:String): List<EventModel>
    suspend fun getTasksByDate(event: EventModel): List<EventModel>
    suspend fun updateEvent(event: EventModel): Void?
    suspend fun deleteEvent(event: EventModel): Void?
}
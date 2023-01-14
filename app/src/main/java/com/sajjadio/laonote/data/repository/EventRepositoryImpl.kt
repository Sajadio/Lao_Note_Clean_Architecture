package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.EventsDocFireStore
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import javax.inject.Inject


class EventRepositoryImpl @Inject constructor(
    private val eventsDocFireStore: EventsDocFireStore
) : EventRepository {

    override suspend fun setEvent(event: EventModel): Void? =
        eventsDocFireStore.setEvent(event)

    override suspend fun getEvents(): List<EventModel> =
        eventsDocFireStore.getEvents()

    override suspend fun getTasksByDate(date: String): List<EventModel> =
        eventsDocFireStore.getTasksByDate(date)

    override suspend fun updateEvent(event: EventModel): Void? =
        eventsDocFireStore.updateEvent(event)

    override suspend fun deleteEventByID(eventId: String): Void? =
        eventsDocFireStore.deleteEventByID(eventId)
}
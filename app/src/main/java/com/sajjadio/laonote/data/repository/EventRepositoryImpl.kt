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

    override suspend fun getEvents(userID: String): List<EventModel> =
        eventsDocFireStore.getEvents(userID)

    override suspend fun getTasksByDate(event: EventModel): List<EventModel> =
        eventsDocFireStore.getTasksByDate(event)

    override suspend fun updateEvent(event: EventModel): Void? =
        eventsDocFireStore.updateEvent(event)

    override suspend fun deleteEvent(event: EventModel): Void? =
        eventsDocFireStore.deleteEvent(event)
}
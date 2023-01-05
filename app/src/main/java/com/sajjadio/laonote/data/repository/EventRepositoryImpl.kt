package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.repository.EventRepository
import javax.inject.Inject


class EventRepositoryImpl @Inject constructor(
    private val docFireStore: DocumentaryFireStore,
) : EventRepository {

    override suspend fun setEvent(data: HashMap<String, Any?>): Void? =
        docFireStore.setEvent(data)

    override suspend fun getEvents(): List<EventModel> =
        docFireStore.getEvents()

    override suspend fun updateEventByID(data: HashMap<String, Any?>): Void? =
        docFireStore.updateEventByID(data)

    override suspend fun deleteEventByID(eventId: String): Void? =
        docFireStore.deleteEventByID(eventId)
}
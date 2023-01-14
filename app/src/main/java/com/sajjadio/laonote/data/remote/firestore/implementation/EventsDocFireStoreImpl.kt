package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.EventsDocFireStore
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.formatDate
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventsDocFireStoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : EventsDocFireStore {

    override suspend fun setEvent(event: EventModel): Void? {
        val setEvent = HashMap<String, Any?>()
        setEvent[EVENT_ID] = event.event_id
        setEvent[EVENT_TITLE] = event.event_title
        setEvent[EVENT_DESCRIPTION] = event.event_description
        setEvent[EVENT_DATE_CREATED] = event.event_date_created
        val collection = fireStore.collection(EVENTS)
        return collection.document(setEvent[EVENT_ID].toString()).set(setEvent).await()
    }

    override suspend fun getEvents(): List<EventModel> =
        fireStore.collection(EVENTS).get().await().toObjects(EventModel::class.java)

    override suspend fun getTasksByDate(date: String): List<EventModel> {
        val events = mutableListOf<EventModel>()
        if (date.isEmpty()) {
            return getEvents()
        } else {
            getEvents().forEach { result ->
                if (compareBetweenDates(result.event_date_created, date)) {
                    events.add(result)
                }
            }
        }
        return events
    }

    override suspend fun updateEvent(event: EventModel): Void? {

        val updateEvent = HashMap<String, Any?>()
        updateEvent[EVENT_ID] = event.event_id
        updateEvent[EVENT_TITLE] = event.event_title
        updateEvent[EVENT_DESCRIPTION] = event.event_description
        updateEvent[EVENT_DATE_CREATED] = event.event_date_created
        return fireStore.collection(EVENTS).document(updateEvent[EVENT_ID].toString())
            .update(updateEvent).await()
    }

    override suspend fun deleteEventByID(eventId: String): Void? =
        fireStore.collection(EVENTS).document(eventId).delete().await()

    private fun compareBetweenDates(eventDateCreated: String?, date: String): Boolean {
        val firstDate = eventDateCreated.formatDate(FULL_DATE, MONTH_DAY_YEAR)
        return firstDate.equals(date)
    }

}
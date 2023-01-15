package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.EventsDocFireStore
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.formatDate
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventsDocFireStoreImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : EventsDocFireStore {

    private val userCollection = fireStore.collection(USER)

    override suspend fun setEvent(event: EventModel): Void? {
        val setEvent = HashMap<String, Any?>()
        setEvent[USER_ID] = event.user_id
        setEvent[EVENT_ID] = event.event_id
        setEvent[EVENT_TITLE] = event.event_title
        setEvent[EVENT_DESCRIPTION] = event.event_description
        setEvent[EVENT_DATE_CREATED] = event.event_date_created

        return userCollection
            .document(setEvent[USER_ID].toString())
            .collection(EVENTS)
            .document(setEvent[EVENT_ID].toString())
            .set(setEvent).await()
    }

    override suspend fun getEvents(userID: String): List<EventModel> =
        userCollection.document(userID).collection(EVENTS).get().await()
            .toObjects(EventModel::class.java)

    override suspend fun getTasksByDate(event: EventModel): List<EventModel> {
        val events = mutableListOf<EventModel>()
        if (event.event_date_created?.isEmpty() == true) return getEvents(event.user_id.toString())
        getEvents(event.user_id.toString()).forEach { result ->
            if (
                compareBetweenDates(
                    result.event_date_created,
                    event.event_date_created.toString()
                )
            ) events.add(result)

        }

        return events
    }

    override suspend fun updateEvent(event: EventModel): Void? {
        val updateEvent = HashMap<String, Any?>()
        updateEvent[USER_ID] = event.user_id
        updateEvent[EVENT_ID] = event.event_id
        updateEvent[EVENT_TITLE] = event.event_title
        updateEvent[EVENT_DESCRIPTION] = event.event_description
        updateEvent[EVENT_DATE_CREATED] = event.event_date_created
        return userCollection
            .document(updateEvent[USER_ID].toString())
            .collection(EVENTS)
            .document(updateEvent[EVENT_ID].toString())
            .update(updateEvent)
            .await()
    }

    override suspend fun deleteEvent(event: EventModel): Void? = userCollection
        .document(event.user_id.toString())
        .collection(EVENTS).document(event.event_id)
        .delete()
        .await()

    private fun compareBetweenDates(eventDateCreated: String?, date: String): Boolean {
        val firstDate = eventDateCreated.formatDate(FULL_DATE, MONTH_DAY_YEAR)
        return firstDate.equals(date)
    }

}
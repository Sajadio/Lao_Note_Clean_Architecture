package com.sajjadio.laonote.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DocumentaryFireStoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : DocumentaryFireStore {


    // note collection
    override suspend fun setNote(data: HashMap<String, Any?>): Void? {
        val collection = fireStore.collection(NOTES)
        return collection.document(data[NOTE_ID].toString()).set(data).await()
    }

    override suspend fun getNotes(): List<Note> =
        fireStore.collection(NOTES).get().await().toObjects(Note::class.java)

    override suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>): Void? =
        fireStore.collection(NOTES).document(noteId).update(data).await()

    override suspend fun deleteNoteByID(noteId: String): Void? =
        fireStore.collection(NOTES).document(noteId).delete().await()


    // tasks collection
    override suspend fun setTask(data: HashMap<String, Any?>): Void? {
        val collection = fireStore.collection(TASKS)
        return collection.document(data[TASK_ID].toString()).set(data).await()
    }

    override suspend fun getTasks(): List<Task> =
        fireStore.collection(TASKS).get().await().toObjects(Task::class.java)

    override suspend fun updateTaskByID(data: HashMap<String, Any?>): Void? =
        fireStore.collection(TASKS).document(data[TASK_ID].toString()).update(data).await()

    override suspend fun deleteTaskByID(taskId: String): Void? =
        fireStore.collection(TASKS).document(taskId).delete().await()


    // events collection
    override suspend fun setEvent(data: HashMap<String, Any?>): Void? {
        val collection = fireStore.collection(EVENTS)
        return collection.document(data[EVENT_ID].toString()).set(data).await()
    }

    override suspend fun getEvents(): List<EventModel> =
        fireStore.collection(EVENTS).get().await().toObjects(EventModel::class.java)

    override suspend fun updateEventByID(data: HashMap<String, Any?>): Void? =
        fireStore.collection(EVENTS).document(data[EVENT_ID].toString()).update(data).await()

    override suspend fun deleteEventByID(eventId: String): Void? =
        fireStore.collection(EVENTS).document(eventId).delete().await()

}
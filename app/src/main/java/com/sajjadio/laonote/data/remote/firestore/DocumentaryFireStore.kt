package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.model.Task

interface DocumentaryFireStore {

    // note collection
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
    suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>): Void?
    suspend fun deleteNoteByID(noteId: String): Void?

    // tasks collection
    suspend fun setTask(data: HashMap<String, Any?>): Void?
    suspend fun getTasks(): List<Task>
    suspend fun updateTaskByID(data: HashMap<String, Any?>): Void?
    suspend fun deleteTaskByID(taskId: String): Void?

    // events collection
    suspend fun setEvent(data: HashMap<String, Any?>): Void?
    suspend fun getEvents(): List<EventModel>
    suspend fun updateEventByID(data: HashMap<String, Any?>): Void?
    suspend fun deleteEventByID(eventId: String): Void?
}
package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.model.Task

interface DocumentaryFireStore {
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
    suspend fun updateNoteByID(noteId:String, data: HashMap<String, Any?>): Void?
    suspend fun deleteNoteByID(noteId:String): Void?

    suspend fun setTask(data: HashMap<String, Any?>): Void?
    suspend fun getTasks(): List<Task>
    suspend fun updateTaskByID(data: HashMap<String, Any?>): Void?
    suspend fun deleteTaskByID(taskId:String): Void?
}
package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Note

interface DocumentaryFireStore {
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
    suspend fun updateNoteByID(noteId:String, data: HashMap<String, Any?>): Void?
    suspend fun deleteNoteByID(noteId:String): Void?
}
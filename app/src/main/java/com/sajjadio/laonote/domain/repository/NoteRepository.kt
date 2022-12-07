package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.Note


interface NoteRepository {
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
    suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>): Void?
    suspend fun deleteNoteByID(noteId: String): Void?
}
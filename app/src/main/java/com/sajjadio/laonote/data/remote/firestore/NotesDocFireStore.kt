package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Note

interface NotesDocFireStore {
    // note collection
    suspend fun setNote(note: Note): Void?
    suspend fun getNotes(): List<Note>
    suspend fun getNotesByTitle(title:String): List<Note>
    suspend fun updateNote(note: Note): Void?
    suspend fun deleteNoteByID(noteId: String): Void?
}
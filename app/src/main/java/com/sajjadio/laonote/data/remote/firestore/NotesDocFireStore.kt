package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Note

interface NotesDocFireStore {
    // note collection
    suspend fun setNote(note: Note): Void?
    suspend fun getNotes(userID:String): List<Note>
    suspend fun searchAboutNote(note: Note): List<Note>
    suspend fun updateNote(note: Note): Void?
    suspend fun deleteNote(note: Note): Void?
}
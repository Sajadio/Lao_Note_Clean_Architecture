package com.sajjadio.laonote.domain.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.sajjadio.laonote.domain.model.Note


interface NoteRepository {
    suspend fun setNote(note: Note): Void?
    suspend fun getNotes(): List<Note>
    suspend fun getNotesByTitle(title: String): List<Note>
    suspend fun updateNote(note: Note): Void?
    suspend fun deleteNoteByID(noteId: String): Void?

    fun manageImageStorage(uri: Uri): UploadTask
}
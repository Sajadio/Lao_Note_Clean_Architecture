package com.sajjadio.laonote.domain.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.sajjadio.laonote.domain.model.Note


interface NoteRepository {
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
    suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>): Void?
    suspend fun deleteNoteByID(noteId: String): Void?

    fun manageImageStorage(uri: Uri): UploadTask
}
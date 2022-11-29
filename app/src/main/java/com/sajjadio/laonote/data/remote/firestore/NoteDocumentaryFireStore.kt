package com.sajjadio.laonote.data.remote.firestore

import com.sajjadio.laonote.domain.model.Note

interface NoteDocumentaryFireStore {
    suspend fun setNote(data: HashMap<String, Any?>): Void?
    suspend fun getNotes(): List<Note>
}
package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val docFireStore: DocumentaryFireStore,
) : NoteRepository {

    override suspend fun setNote(data: HashMap<String, Any?>) = docFireStore.setNote(data)

    override suspend fun getNotes() = docFireStore.getNotes()

    override suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>) =
        docFireStore.updateNoteByID(noteId, data)

    override suspend fun deleteNoteByID(noteId: String) = docFireStore.deleteNoteByID(noteId)
}
package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.firestore.NoteDocumentaryFireStore
import com.sajjadio.laonote.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteFireStore: NoteDocumentaryFireStore
) : NoteRepository {

    override suspend fun setNote(data: HashMap<String, Any?>) = noteFireStore.setNote(data)

    override suspend fun getNotes()  = noteFireStore.getNotes()
}
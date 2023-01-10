package com.sajjadio.laonote.data.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.data.remote.firestore.FireStorage
import com.sajjadio.laonote.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val docFireStore: DocumentaryFireStore,
    private val storage: FireStorage
) : NoteRepository {

    override suspend fun setNote(data: HashMap<String, Any?>) = docFireStore.setNote(data)

    override suspend fun getNotes() = docFireStore.getNotes()

    override suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>) =
        docFireStore.updateNoteByID(noteId, data)

    override suspend fun deleteNoteByID(noteId: String) = docFireStore.deleteNoteByID(noteId)

    override fun manageImageStorage(uri: Uri): UploadTask =
        storage.manageImageStorage(uri)

}
package com.sajjadio.laonote.data.repository

import android.net.Uri
import com.sajjadio.laonote.data.remote.firestore.NotesDocFireStore
import com.sajjadio.laonote.data.remote.firestore.storage.FireStorage
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val notesDocFireStore: NotesDocFireStore,
    private val storage: FireStorage
) : NoteRepository {

    override suspend fun setNote(note: Note) =
        notesDocFireStore.setNote(note)

    override suspend fun getNotes(userID: String) =
        notesDocFireStore.getNotes(userID)

    override suspend fun searchAboutNote(note: Note) =
        notesDocFireStore.searchAboutNote(note)

    override suspend fun updateNote(note: Note) =
        notesDocFireStore.updateNote(note)

    override suspend fun deleteNote(note: Note) =
        notesDocFireStore.deleteNote(note)

    override fun manageImageStorage(uri: Uri) =
        storage.manageImageStorage(uri)

}
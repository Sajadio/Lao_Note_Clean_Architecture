package com.sajjadio.laonote.data.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.sajjadio.laonote.data.remote.firestore.NotesDocFireStore
import com.sajjadio.laonote.data.remote.firestore.storage.FireStorage
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val notesDocFireStore: NotesDocFireStore,
    private val storage: FireStorage
) : NoteRepository {

    override suspend fun setNote(note: Note) = notesDocFireStore.setNote(note)

    override suspend fun getNotes() = notesDocFireStore.getNotes()

    override suspend fun getNotesByTitle(title: String): List<Note> =
        notesDocFireStore.getNotesByTitle(title)

    override suspend fun updateNote(note: Note) =
        notesDocFireStore.updateNote(note)

    override suspend fun deleteNoteByID(noteId: String) = notesDocFireStore.deleteNoteByID(noteId)

    override fun manageImageStorage(uri: Uri): UploadTask = storage.manageImageStorage(uri)

}
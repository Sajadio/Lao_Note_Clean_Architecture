package com.sajjadio.laonote.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.utils.NOTES
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class DocumentaryFireStoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : DocumentaryFireStore {


    override suspend fun setNote(data: HashMap<String, Any?>): Void? {
        val collection = fireStore.collection(NOTES)
        return collection.document(data["note_id"].toString()).set(data).await()
    }

    override suspend fun getNotes(): List<Note> =
        fireStore.collection(NOTES).get().await().toObjects(Note::class.java)

    override suspend fun updateNoteByID(noteId: String, data: HashMap<String, Any?>): Void? =
        fireStore.collection(NOTES).document(noteId).update(data).await()

    override suspend fun deleteNoteByID(noteId: String): Void? =
        fireStore.collection(NOTES).document(noteId).delete().await()
}
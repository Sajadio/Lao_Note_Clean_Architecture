package com.sajjadio.laonote.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.utils.NOTES
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class NoteFireStoreDocumentaryNoteImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : NoteDocumentaryFireStore {


    override suspend fun setNote(data: HashMap<String, Any?>): Void? {
        val collection = fireStore.collection(NOTES)
        val document = UUID.randomUUID().toString()
        return collection.document(document).set(data).await()
    }

    override suspend fun getNotes(): List<Note> =  fireStore.collection(NOTES).get().await().toObjects(Note::class.java)
}
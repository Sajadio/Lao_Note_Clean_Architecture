package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.NotesDocFireStore
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotesDocFireStoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : NotesDocFireStore {
    override suspend fun setNote(note: Note): Void? {

        val setNote = HashMap<String, Any?>()
        setNote[NOTE_ID] = note.note_id
        setNote[NOTE_TITLE] = note.note_title
        setNote[NOTE_SUB_TITLE] = note.note_subTitle
        setNote[NOTE_DESCRIPTION] = note.note_description
        setNote[NOTE_IMAGE] = note.note_image
        setNote[NOTE_WEB_URL] = note.note_webUrl
        setNote[NOTE_COLOR] = note.note_color
        setNote[FONT_COLOR] = note.font_color
        setNote[NOTE_DATE_CREATED] = note.note_date_created

        val collection = fireStore.collection(NOTES)
        return collection.document(setNote[NOTE_ID].toString()).set(setNote).await()
    }

    override suspend fun getNotes(): List<Note> =
        fireStore.collection(NOTES).get().await().toObjects(Note::class.java)

    override suspend fun getNotesByTitle(title: String): List<Note> {
        val notes = mutableListOf<Note>()
        if (title.isEmpty()) {
            return getNotes()
        }
        getNotes().forEach { note ->
            if ((note.note_title?.startsWith(title, true) == true) ||
                note.note_title?.endsWith(title, true) == true
            ) {
                notes.add(note)
            }
        }
        return notes
    }

    override suspend fun updateNote(note: Note): Void? {

        val updateNote = HashMap<String, Any?>()
        updateNote[NOTE_ID] = note.note_id
        updateNote[NOTE_TITLE] = note.note_title
        updateNote[NOTE_SUB_TITLE] = note.note_subTitle
        updateNote[NOTE_DESCRIPTION] = note.note_description
        updateNote[NOTE_IMAGE] = note.note_image
        updateNote[NOTE_WEB_URL] = note.note_webUrl
        updateNote[NOTE_COLOR] = note.note_color
        updateNote[FONT_COLOR] = note.font_color
        updateNote[NOTE_LATS_UPDATE] = note.note_last_update

        return fireStore.collection(NOTES).document(note.note_id).update(updateNote).await()
    }

    override suspend fun deleteNoteByID(noteId: String): Void? =
        fireStore.collection(NOTES).document(noteId).delete().await()
}
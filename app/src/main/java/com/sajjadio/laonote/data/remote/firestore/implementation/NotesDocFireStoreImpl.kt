package com.sajjadio.laonote.data.remote.firestore.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.sajjadio.laonote.data.remote.firestore.NotesDocFireStore
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotesDocFireStoreImpl @Inject constructor(
    fireStore: FirebaseFirestore,
) : NotesDocFireStore {

    private val userCollection = fireStore.collection(USER)

    override suspend fun setNote(note: Note): Void? {
        val setNote = HashMap<String, Any?>()
        setNote[USER_ID] = note.user_id
        setNote[NOTE_ID] = note.note_id
        setNote[NOTE_TITLE] = note.note_title
        setNote[NOTE_SUB_TITLE] = note.note_subTitle
        setNote[NOTE_DESCRIPTION] = note.note_description
        setNote[NOTE_IMAGE] = note.note_image
        setNote[NOTE_WEB_URL] = note.note_webUrl
        setNote[NOTE_COLOR] = note.note_color
        setNote[FONT_COLOR] = note.font_color
        setNote[NOTE_DATE_CREATED] = note.note_date_created

        return userCollection
            .document(setNote[USER_ID].toString())
            .collection(NOTES)
            .document(setNote[NOTE_ID].toString())
            .set(setNote)
            .await()
    }

    override suspend fun getNotes(userID: String): List<Note> = userCollection
        .document(userID)
        .collection(NOTES)
        .get()
        .await()
        .toObjects(Note::class.java)

    override suspend fun searchAboutNote(note: Note): List<Note> {
        val notes = mutableListOf<Note>()
        if (note.note_title?.isEmpty() == true) {
            return getNotes(note.user_id.toString())
        }
        getNotes(note.user_id.toString()).forEach {
            if ((it.note_title?.startsWith(note.note_title.toString(), true) == true) ||
                it.note_title?.endsWith(note.note_title.toString(), true) == true
            ) {
                notes.add(it)
            }
        }
        return notes

    }

    override suspend fun updateNote(note: Note): Void? {
        val updateNote = HashMap<String, Any?>()
        updateNote[USER_ID] = note.user_id
        updateNote[NOTE_ID] = note.note_id
        updateNote[NOTE_TITLE] = note.note_title
        updateNote[NOTE_SUB_TITLE] = note.note_subTitle
        updateNote[NOTE_DESCRIPTION] = note.note_description
        updateNote[NOTE_IMAGE] = note.note_image
        updateNote[NOTE_WEB_URL] = note.note_webUrl
        updateNote[NOTE_COLOR] = note.note_color
        updateNote[FONT_COLOR] = note.font_color
        updateNote[NOTE_LATS_UPDATE] = note.note_last_update

        return userCollection
            .document(updateNote[USER_ID].toString())
            .collection(NOTES)
            .document(updateNote[NOTE_ID].toString())
            .update(updateNote).await()
    }

    override suspend fun deleteNote(note: Note): Void? = userCollection
        .document(note.user_id.toString())
        .collection(NOTES)
        .document(note.note_id)
        .delete()
        .await()

}
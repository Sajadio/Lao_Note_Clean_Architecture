package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.HashMap

class SetNoteUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    suspend operator fun invoke(note: Note) = flow {
        try {
            emit(NetworkResponse.Loading)
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
            val response = noteRepo.setNote(setNote)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
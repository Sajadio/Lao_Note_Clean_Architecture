package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.dateFormat
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class UpdateNoteByIDUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    suspend operator fun invoke(note: Note) = flow {
        try {
            emit(NetworkResponse.Loading)
            val updateNote = HashMap<String, Any?>()
            updateNote[NOTE_ID] = note.note_id
            updateNote[NOTE_TITLE] = note.note_title
            updateNote[NOTE_SUB_TITLE] = note.note_subTitle
            updateNote[NOTE_DESCRIPTION] = note.note_description
            updateNote[NOTE_WEB_URL] = note.note_webUrl
            updateNote[NOTE_COLOR] = note.note_color
            updateNote[FONT_COLOR] = note.font_color
            updateNote[NOTE_LATS_UPDATE] = Calendar.getInstance().time.toString().dateFormat()
            val response = noteRepo.updateNoteByID(note.note_id, updateNote)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
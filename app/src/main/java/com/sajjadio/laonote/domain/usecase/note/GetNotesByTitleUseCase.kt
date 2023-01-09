package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesByTitleUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    operator fun invoke(title: String) = flow {
        try {
            val notes = mutableListOf<Note>()
            emit(NetworkResponse.Loading)
            val response = noteRepo.getNotes()
            response.forEach { result ->
                if (
                    (result.note_title?.startsWith(title, true) == true) ||
                    result.note_title?.endsWith(title, true) == true
                ) {
                    notes.add(result)
                    emit(NetworkResponse.Success(notes.toList()))
                } else
                    emit(NetworkResponse.Success(notes.toList()))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
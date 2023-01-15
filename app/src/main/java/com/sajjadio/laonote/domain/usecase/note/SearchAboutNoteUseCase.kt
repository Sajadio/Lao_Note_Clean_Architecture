package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAboutNoteUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    operator fun invoke(note: Note) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = noteRepo.searchAboutNote(note)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
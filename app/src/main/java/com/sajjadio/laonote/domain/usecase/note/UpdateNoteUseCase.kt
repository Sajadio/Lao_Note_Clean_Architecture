package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    suspend operator fun invoke(note: Note) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = noteRepo.updateNote(note)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
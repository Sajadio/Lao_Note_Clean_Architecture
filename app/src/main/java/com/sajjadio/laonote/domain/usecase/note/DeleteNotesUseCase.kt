package com.sajjadio.laonote.domain.usecase.note

import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    suspend operator fun invoke(noteId: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = noteRepo.deleteNoteByID(noteId)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
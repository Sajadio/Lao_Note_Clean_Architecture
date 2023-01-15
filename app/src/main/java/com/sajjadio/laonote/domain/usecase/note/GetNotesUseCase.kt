package com.sajjadio.laonote.domain.usecase.note

import android.util.Log
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.TAG
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepo: NoteRepository
) {
    operator fun invoke(userID:String) = flow {
        try {
            emit(NetworkResponse.Loading)
            Log.d(TAG, "invoke: $userID")
            val response = noteRepo.getNotes(userID)
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
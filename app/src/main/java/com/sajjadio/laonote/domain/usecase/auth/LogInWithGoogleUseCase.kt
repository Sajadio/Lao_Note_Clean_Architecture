package com.sajjadio.laonote.domain.usecase.auth

import androidx.activity.result.ActivityResult
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.model.Authentication
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(result: ActivityResult) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = authRepository.checkSignInWithGoogle(result)?.user?.uid
            emit(NetworkResponse.Success(response))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }

}
package com.sajjadio.laonote.domain.usecase.auth

import androidx.activity.result.ActivityResult
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
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
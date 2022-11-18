package com.sajjadio.laonote.domain.usecase.auth

import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.model.Authentication
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(authentication: Authentication) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = authRepository.logIn(
                authentication.email,
                authentication.password
            ).await()
            val token = response.user?.uid
            emit(NetworkResponse.Success(token))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }

}
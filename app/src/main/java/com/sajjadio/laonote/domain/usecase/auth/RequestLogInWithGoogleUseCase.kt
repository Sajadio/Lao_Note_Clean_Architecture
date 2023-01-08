package com.sajjadio.laonote.domain.usecase.auth

import com.sajjadio.laonote.domain.repository.AuthRepository
import javax.inject.Inject

class RequestLogInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() = authRepository.requestSignInWithGoogle()
}
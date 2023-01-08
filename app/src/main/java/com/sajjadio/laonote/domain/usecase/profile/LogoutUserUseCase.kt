package com.sajjadio.laonote.domain.usecase.profile

import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager
) {

    suspend operator fun invoke() {
        sessionManager.logout()
        profileRepository.logOut()
    }
}
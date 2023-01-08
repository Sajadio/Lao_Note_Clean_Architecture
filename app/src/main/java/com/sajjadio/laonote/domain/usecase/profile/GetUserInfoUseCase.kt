package com.sajjadio.laonote.domain.usecase.profile

import com.sajjadio.laonote.domain.repository.ProfileRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke()= flow {
        try {
            emit(NetworkResponse.Loading)
            emit(NetworkResponse.Success(profileRepository.getUserInfo()))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}
package com.sajjadio.laonote.data.repository

import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.domain.model.User
import com.sajjadio.laonote.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: Authentication
) : ProfileRepository {
    override fun getUserInfo(): User = auth.getUserInfo()
    override fun logOut() = auth.logOut()
}
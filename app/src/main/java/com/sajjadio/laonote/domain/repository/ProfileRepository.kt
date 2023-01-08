package com.sajjadio.laonote.domain.repository

import com.sajjadio.laonote.domain.model.User

interface ProfileRepository {
    fun getUserInfo(): User
    fun logOut()
}
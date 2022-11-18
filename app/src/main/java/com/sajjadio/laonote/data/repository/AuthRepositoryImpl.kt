package com.sajjadio.laonote.data.repository


import androidx.activity.result.ActivityResult
import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.utils.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Authentication
) : AuthRepository {

    override fun logIn(email: String, password: String) = auth.logIn(email, password)

    override suspend fun requestSignInWithGoogle() = auth.requestSignInWithGoogle()

    override suspend fun checkSignInWithGoogle(result: ActivityResult) = auth.checkSignInWithGoogle(result)

    override fun sendPasswordReset(email: String) = auth.sendPasswordReset(email)

    override fun logOut() = auth.logOut()
}
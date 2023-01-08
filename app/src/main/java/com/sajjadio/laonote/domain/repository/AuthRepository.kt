package com.sajjadio.laonote.domain.repository

import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {


    fun logIn(email: String, password: String): Task<AuthResult>

    suspend fun requestSignInWithGoogle(): GoogleSignInClient

    suspend fun checkSignInWithGoogle(result: ActivityResult): AuthResult?

    fun sendPasswordReset(email: String): Task<Void>
}
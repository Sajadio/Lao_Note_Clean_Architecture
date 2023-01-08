package com.sajjadio.laonote.data.remote.auth

import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.sajjadio.laonote.domain.model.User

interface Authentication {

    fun getUserInfo():User

    fun logIn(email: String, password: String): Task<AuthResult>

    fun sendPasswordReset(email: String): Task<Void>

    suspend fun requestSignInWithGoogle(): GoogleSignInClient

    suspend fun checkSignInWithGoogle(result: ActivityResult): AuthResult?

    fun logOut()
}
package com.sajjadio.laonote.data.remote.auth


import android.content.Context
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sajjadio.laonote.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : Authentication {

    override fun logIn(
        email: String,
        password: String,
    ) = auth.signInWithEmailAndPassword(email, password)

    override suspend fun requestSignInWithGoogle(): GoogleSignInClient {
        val googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, googleSignIn)
    }

    override suspend fun checkSignInWithGoogle(result: ActivityResult): AuthResult? {
        val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val account = accountTask.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return auth.signInWithCredential(credential).await()
    }

    override fun sendPasswordReset(email: String) = auth.sendPasswordResetEmail(email)

    override fun logOut() = auth.signOut()
}
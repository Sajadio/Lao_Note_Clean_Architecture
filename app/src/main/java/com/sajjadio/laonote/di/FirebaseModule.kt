package com.sajjadio.laonote.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.data.remote.auth.AuthenticationImpl
import com.sajjadio.laonote.data.remote.firestore.NoteDocumentaryFireStore
import com.sajjadio.laonote.data.remote.firestore.NoteFireStoreDocumentaryNoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFireStoreDocumentary(fireStore: FirebaseFirestore): NoteDocumentaryFireStore {
        return NoteFireStoreDocumentaryNoteImpl(fireStore)
    }

    @Singleton
    @Provides
    fun provideAuthentication(
        auth: FirebaseAuth,
        @ApplicationContext context: Context
    ): Authentication =
        AuthenticationImpl(auth, context)

}
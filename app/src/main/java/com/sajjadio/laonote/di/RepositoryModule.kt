package com.sajjadio.laonote.di


import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.data.repository.*
import com.sajjadio.laonote.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideAuthRepository(auth: Authentication): AuthRepository = AuthRepositoryImpl(auth)

    @Singleton
    @Provides
    fun provideProfileRepository(auth: Authentication): ProfileRepository =
        ProfileRepositoryImpl(auth)

    @Singleton
    @Provides
    fun provideNoteRepository(documentaryFireStore: DocumentaryFireStore): NoteRepository =
        NoteRepositoryImpl(documentaryFireStore)

    @Singleton
    @Provides
    fun provideTaskRepository(documentaryFireStore: DocumentaryFireStore): TaskRepository =
        TaskRepositoryImpl(documentaryFireStore)

    @Singleton
    @Provides
    fun provideEventRepository(documentaryFireStore: DocumentaryFireStore): EventRepository =
        EventRepositoryImpl(documentaryFireStore)

}
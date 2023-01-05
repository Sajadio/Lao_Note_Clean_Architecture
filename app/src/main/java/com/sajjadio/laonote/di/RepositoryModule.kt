package com.sajjadio.laonote.di


import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.data.remote.firestore.DocumentaryFireStore
import com.sajjadio.laonote.data.repository.AuthRepositoryImpl
import com.sajjadio.laonote.data.repository.EventRepositoryImpl
import com.sajjadio.laonote.data.repository.NoteRepositoryImpl
import com.sajjadio.laonote.data.repository.TaskRepositoryImpl
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.domain.repository.EventRepository
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.domain.repository.TaskRepository
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
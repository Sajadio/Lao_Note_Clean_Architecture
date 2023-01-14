package com.sajjadio.laonote.di


import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.data.remote.firestore.EventsDocFireStore
import com.sajjadio.laonote.data.remote.firestore.NotesDocFireStore
import com.sajjadio.laonote.data.remote.firestore.TasksDocFireStore
import com.sajjadio.laonote.data.remote.firestore.storage.FireStorage
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
    fun provideNoteRepository(
        documentaryFireStore: NotesDocFireStore,
        fireStorage: FireStorage
    ): NoteRepository =
        NoteRepositoryImpl(documentaryFireStore, fireStorage)

    @Singleton
    @Provides
    fun provideTaskRepository(documentaryFireStore: TasksDocFireStore): TaskRepository =
        TaskRepositoryImpl(documentaryFireStore)

    @Singleton
    @Provides
    fun provideEventRepository(documentaryFireStore: EventsDocFireStore): EventRepository =
        EventRepositoryImpl(documentaryFireStore)


}
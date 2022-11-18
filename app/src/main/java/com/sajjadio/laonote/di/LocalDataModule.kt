package com.sajjadio.laonote.di

import android.content.Context
import androidx.room.Room
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.data.local.data_storage.SessionManagerImpl
import com.sajjadio.laonote.data.local.db.LaoNoteDB
import com.sajjadio.laonote.utils.LAO_NOTE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        LaoNoteDB::class.java,
        LAO_NOTE_DB
    ).build()

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }

}
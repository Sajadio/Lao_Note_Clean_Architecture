package com.sajjadio.laonote.di

import android.content.Context
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.data.local.data_storage.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }

}
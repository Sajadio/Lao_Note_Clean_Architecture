package com.sajjadio.laonote.di

import android.content.Context
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.data.local.data_storage.SessionManagerImpl
import com.sajjadio.laonote.data.local.data_storage.UIThemeStorageManager
import com.sajjadio.laonote.data.local.data_storage.UIThemeStorageManagerImpl
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
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }

    @Singleton
    @Provides
    fun provideUIThemeStorageManager(@ApplicationContext context: Context): UIThemeStorageManager {
        return UIThemeStorageManagerImpl(context)
    }

}
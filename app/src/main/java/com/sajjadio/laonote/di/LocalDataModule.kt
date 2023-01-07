package com.sajjadio.laonote.di

import android.content.Context
import com.sajjadio.laonote.data.local.data_storage.LocalDataStorage
import com.sajjadio.laonote.data.local.data_storage.LocalDataStorageImpl
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
    fun providePreferenceManager(@ApplicationContext context: Context): LocalDataStorage {
        return LocalDataStorageImpl(context)
    }

}
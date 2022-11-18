package com.sajjadio.laonote.di



import com.sajjadio.laonote.data.remote.auth.Authentication
import com.sajjadio.laonote.data.repository.AuthRepositoryImpl
import com.sajjadio.laonote.domain.repository.AuthRepository
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

}
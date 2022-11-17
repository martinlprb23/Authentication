package com.roblescode.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.roblescode.authentication.data.AuthRepository
import com.roblescode.authentication.data.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl) : AuthRepository = impl
}
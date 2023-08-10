package com.krunal.mychat.di

import com.krunal.mychat.data.repository.AuthRepository
import com.krunal.mychat.data.repository.AuthRepositoryImpl
import com.krunal.mychat.data.repository.UserRepository
import com.krunal.mychat.data.repository.UserRepositoryImpl
import com.krunal.mychat.utils.ResourceHelper
import com.krunal.mychat.utils.ResourceHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

/**
 * Defines all the classes that need to be provided in the scope of the app.
 * If they are singleton mark them as '@Singleton'.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {

    @Singleton
    @Binds
    abstract fun bindResourceHelper(impl: ResourceHelperImpl): ResourceHelper

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}

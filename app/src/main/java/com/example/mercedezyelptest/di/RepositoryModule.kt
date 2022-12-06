package com.example.mercedezyelptest.di

import com.example.mercedezyelptest.model.Repository
import com.example.mercedezyelptest.model.RepositoryImpl
import com.example.mercedezyelptest.model.remote.YelpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(service: YelpApi): Repository{
        return RepositoryImpl(service)
    }
}
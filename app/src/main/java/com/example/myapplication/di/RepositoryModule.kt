package com.example.myapplication.di

import com.example.myapplication.data.repositories.CardRepository
import com.example.myapplication.data.datasource.remote.CardDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideCardRepository(
    networkClient: CardDataSource,
    coroutineDispatcher: CoroutineDispatcher
  ): CardRepository {
    return CardRepository(networkClient, coroutineDispatcher)
  }
}

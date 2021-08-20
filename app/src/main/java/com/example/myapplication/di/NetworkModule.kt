package com.example.myapplication.di

import com.example.myapplication.net.HttpRequestInterceptor
import com.example.myapplication.data.datasource.remote.CardDataSource
import com.example.myapplication.net.NetworkService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpRequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://pokeapi.co/api/v2/")
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideNetworkService(retrofit: Retrofit): NetworkService {
    return retrofit.create(NetworkService::class.java)
  }

  @Provides
  @Singleton
  fun provideNetworkClient(networkService: NetworkService): CardDataSource {
    return CardDataSource(networkService)
  }
}

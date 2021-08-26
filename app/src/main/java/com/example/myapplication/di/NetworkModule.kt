package com.example.myapplication.di

import com.example.myapplication.net.HttpRequestInterceptor
import com.example.myapplication.data.datasource.remote.CardDataSource
import com.example.myapplication.data.model.response.*
import com.example.myapplication.net.NetworkService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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

  @ExperimentalSerializationApi
  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://raw.githubusercontent.com/AmirrezaRotamian/Tech-Challenge/master/")
      .addConverterFactory(
        Json {
          classDiscriminator = "code"
          serializersModule = SerializersModule {
              polymorphic(BaseCard::class) {
                  subclass(Picture::class, Picture.serializer())
                  subclass(Sound::class, Sound.serializer())
                  subclass(Vibrate::class, Vibrate.serializer())
              }
          }
        }.asConverterFactory("application/json".toMediaType()))
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

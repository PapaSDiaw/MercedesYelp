package com.example.mercedezyelptest.di

import com.example.mercedezyelptest.model.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideServiceApi(retrofitClient: Retrofit): YelpApi{
        return retrofitClient.create(YelpApi::class.java)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, converter: Converter.Factory) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .build()


    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.HEADERS)
                }
            )
            .build()
    }

    @Provides
    fun providesInterceptor(): Interceptor{
        return Interceptor {
            it.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(HEADER_BEARER, API_KEY)
                        .addHeader(HEADER_CONT_TYPE, CONT_TYPE)
                        .build()
                )
            }
        }
    }

    @Provides
    fun providesConverter(): Converter.Factory = GsonConverterFactory.create()
}
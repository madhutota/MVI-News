package com.newsmvi.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.newsmvi.data.api.ApiKeyInterceptor
import com.newsmvi.data.api.ApiService
import com.newsmvi.data.local.AppConstants
import com.newsmvi.data.repository.NewsRepository
import com.newsmvi.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            android.util.Log.d("OkHttp", message)
        }.apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor).build()
    }


    @ApiKey
    @Provides
    fun provideApiKey(): String = AppConstants.API_KEY

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = AppConstants.BASE_URL


    @Provides
    @Singleton
    fun provideSchoolApiService(
        okHttpClient: OkHttpClient, @BaseUrl baseUrl: String
    ): ApiService {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType)).baseUrl(baseUrl).build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: ApiService,
    ): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

}
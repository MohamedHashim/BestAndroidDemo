package com.backbase.assignment.core.di

import com.backbase.assignment.BuildConfig
import com.backbase.assignment.core.data.EndPoint
import com.backbase.assignment.core.data.RequestInterceptor
import com.backbase.assignment.features.movies.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     *  provide retrofit singleton instance for service
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EndPoint.theMovieDB)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /**
     *  create okhttp client with logging interceptor in debug mode
     */
    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder().addInterceptor(RequestInterceptor())
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    /**
     *  provide movie service for repository
     */
    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}

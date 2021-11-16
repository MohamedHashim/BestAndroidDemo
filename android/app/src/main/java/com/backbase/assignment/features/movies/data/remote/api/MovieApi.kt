package com.backbase.assignment.features.movies.data.remote.api

import com.backbase.assignment.core.data.EndPoint
import com.backbase.assignment.features.movies.data.remote.model.NowPlayingMovieResponse
import com.backbase.assignment.features.movies.data.remote.model.PopularMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

interface MovieApi {

    @GET("now_playing/")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = EndPoint.languageQuery,
        @Query("page") page: String = EndPoint.pageUnDefinedQuery
    ): Response<NowPlayingMovieResponse>

    @GET("popular/")
    suspend fun getPopularMovies(
        @Query("language") language: String = EndPoint.languageQuery,
        @Query("page") page: String = EndPoint.pageUnDefinedQuery
    ): Response<PopularMovieResponse>
}

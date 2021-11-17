package com.backbase.assignment.core.data.remote.api

import com.backbase.assignment.core.data.EndPoint
import com.backbase.assignment.features.moviedetails.data.remote.model.MovieDetailsResponse
import com.backbase.assignment.features.movies.data.remote.model.NowPlayingMovieResponse
import com.backbase.assignment.features.movies.data.remote.model.PopularMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

interface MovieApi {

    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = EndPoint.languageQuery,
        @Query("page") page: String = EndPoint.pageUnDefinedQuery
    ): Response<NowPlayingMovieResponse>

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = EndPoint.languageQuery,
        @Query("page") page: String = EndPoint.pageUnDefinedQuery
    ): Response<PopularMovieResponse>

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String = EndPoint.languageQuery
    ): Response<MovieDetailsResponse>
}

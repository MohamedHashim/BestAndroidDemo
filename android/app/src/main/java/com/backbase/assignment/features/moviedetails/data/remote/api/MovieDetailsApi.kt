package com.backbase.assignment.features.moviedetails.data.remote.api

import com.backbase.assignment.core.data.EndPoint
import com.backbase.assignment.features.moviedetails.data.remote.model.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

interface MovieDetailsApi {

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Query("language") language: String = EndPoint.languageQuery,
        @Path("id") id: Int
    ): Response<MovieDetailsResponse>
}

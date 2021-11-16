package com.backbase.assignment.core.data

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

object EndPoint {
    const val theMovieDB = "https://api.themoviedb.org/3/movie/"
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val languageQuery = "en-US"
    const val pageUnDefinedQuery = "undefined"

    fun getPosterPath(posterPath: String) = BASE_POSTER_PATH + posterPath
}

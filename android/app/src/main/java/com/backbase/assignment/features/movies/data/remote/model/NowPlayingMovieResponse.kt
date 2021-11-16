package com.backbase.assignment.features.movies.data.remote.model

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class NowPlayingMovieResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)

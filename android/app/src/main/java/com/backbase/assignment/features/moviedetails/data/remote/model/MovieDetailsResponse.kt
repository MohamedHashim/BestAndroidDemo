package com.backbase.assignment.features.moviedetails.data.remote.model

import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

data class MovieDetailsResponse(
    val poster_path: String,
    val runtime: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val genres: List<Genre>
) {
    fun toDomainObject() =
        MovieDetails(poster_path, runtime, title, overview, release_date, genres)
}

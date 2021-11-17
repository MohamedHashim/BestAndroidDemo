package com.backbase.assignment.features.movies.data.remote.model

import com.backbase.assignment.features.movies.domain.model.Movie

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class MovieResponse(
    val id: Int,
    val title: String,
    val vote_average: Float,
    val release_date: String,
    val poster_path: String
) {
    fun toDomainObject() = Movie(id, title, vote_average.toInt(), release_date, poster_path)
}

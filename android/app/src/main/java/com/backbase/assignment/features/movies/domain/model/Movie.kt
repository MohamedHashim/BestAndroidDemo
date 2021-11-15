package com.backbase.assignment.features.movies.domain.model

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class Movie(
    val id: Long,
    val title: String,
    val rating: Float,
    val releaseDate: String,
    val posterPath: String
)

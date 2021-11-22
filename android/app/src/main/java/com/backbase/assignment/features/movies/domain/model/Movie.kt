package com.backbase.assignment.features.movies.domain.model

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class Movie(
    val id: Int,
    val title: String,
    val rating: Int,
    val releaseDate: String,
    val posterPath: String,
    val total_pages: Int
)

package com.backbase.assignment.features.movies.presentation.model

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class PopularMoviePresentation(
    val id: Int,
    val title: String,
    val rating: Int,
    val releaseDate: String,
    val posterPath: String,
    val total_pages: Int
)

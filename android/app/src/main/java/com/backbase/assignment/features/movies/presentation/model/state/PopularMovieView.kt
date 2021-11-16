package com.backbase.assignment.features.movies.presentation.model.state

import com.backbase.assignment.features.movies.presentation.model.PopularMoviePresentation

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

data class PopularMovieView(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val movies: List<PopularMoviePresentation>? = null
)

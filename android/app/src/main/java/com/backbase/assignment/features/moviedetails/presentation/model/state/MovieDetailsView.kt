package com.backbase.assignment.features.moviedetails.presentation.model.state

import com.backbase.assignment.features.moviedetails.presentation.model.MovieDetailsPresentation

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

data class MovieDetailsView(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movie: MovieDetailsPresentation? = null
)

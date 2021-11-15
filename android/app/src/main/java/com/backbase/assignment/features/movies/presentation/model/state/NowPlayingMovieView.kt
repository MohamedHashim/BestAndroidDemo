package com.backbase.assignment.features.movies.presentation.model.state

import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

data class NowPlayingMovieView(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val movies: List<NowPlayingMoviePresentation>? = null
)

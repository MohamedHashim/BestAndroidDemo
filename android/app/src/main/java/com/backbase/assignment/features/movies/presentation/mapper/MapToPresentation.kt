package com.backbase.assignment.features.movies.presentation.mapper

import com.backbase.assignment.features.movies.domain.model.Movie
import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

fun Movie.toPresentation() = NowPlayingMoviePresentation(

    posterPath = posterPath
)

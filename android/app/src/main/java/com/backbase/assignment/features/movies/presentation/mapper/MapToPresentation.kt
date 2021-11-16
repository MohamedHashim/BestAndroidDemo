package com.backbase.assignment.features.movies.presentation.mapper

import com.backbase.assignment.features.movies.domain.model.Movie
import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation
import com.backbase.assignment.features.movies.presentation.model.PopularMoviePresentation

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

fun Movie.toPresentation() = NowPlayingMoviePresentation(

    posterPath = posterPath
)

fun Movie.toPopularMoviePresentation() = PopularMoviePresentation(

    title = title,
    rating = rating,
    releaseDate = releaseDate,
    posterPath = posterPath
)

package com.backbase.assignment.features.moviedetails.presentation.mapper

import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import com.backbase.assignment.features.moviedetails.presentation.model.MovieDetailsPresentation

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

fun MovieDetails.toPresentation() = MovieDetailsPresentation(
    poster_path = belongs_to_collection.poster_path,
    runtime = runtime,
    title = title,
    overview = overview,
    release_date = release_date,
    genres = genres
)

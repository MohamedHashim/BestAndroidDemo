package com.backbase.assignment.features.moviedetails.domain.model

import com.backbase.assignment.features.moviedetails.data.remote.model.Genre

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

data class MovieDetails(
    val poster_path: String,
    val runtime: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val genres: List<Genre>
)

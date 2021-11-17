package com.backbase.assignment.features.moviedetails.domain.model

import com.backbase.assignment.features.moviedetails.data.remote.model.BelongsToCollectionResponse

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

data class MovieDetails(
    val belongs_to_collection: BelongsToCollectionResponse,
    val runtime: Int,
    val title: String,
    val overview: String,
    val release_date: String
)

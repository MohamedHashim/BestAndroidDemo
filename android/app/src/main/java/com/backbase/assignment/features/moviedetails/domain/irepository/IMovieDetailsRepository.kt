package com.backbase.assignment.features.moviedetails.domain.irepository

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

interface IMovieDetailsRepository {
    suspend fun movieDetails(id: Int): Flow<Either<Failure, List<MovieDetails>>>
}

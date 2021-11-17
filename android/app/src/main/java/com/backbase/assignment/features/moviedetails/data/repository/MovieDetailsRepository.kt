package com.backbase.assignment.features.moviedetails.data.repository

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.data.remote.api.MovieDetailsApi
import com.backbase.assignment.features.moviedetails.domain.irepository.IMovieDetailsRepository
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */
@Singleton
class MovieDetailsRepository @Inject constructor(
    private val apiService: MovieDetailsApi
) : IMovieDetailsRepository {

    /**
     *  fetch movie details data and emit success and failure response
     */
    override suspend fun movieDetails(id: Int): Flow<Either<Failure, List<MovieDetails>>> = flow {
    }
}

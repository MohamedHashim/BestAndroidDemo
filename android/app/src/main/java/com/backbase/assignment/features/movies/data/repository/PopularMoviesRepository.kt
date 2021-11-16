package com.backbase.assignment.features.movies.data.repository

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.data.remote.api.MovieApi
import com.backbase.assignment.features.movies.domain.irepository.IPopularMoviesRepository
import com.backbase.assignment.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@Singleton
class PopularMoviesRepository @Inject constructor(apiService: MovieApi) : IPopularMoviesRepository {
    override suspend fun popularMovies(): Flow<Either<Failure, List<Movie>>> = flow { }
}

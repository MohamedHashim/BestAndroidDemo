package com.backbase.assignment.features.movies.domain.irepository

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

interface IPopularMoviesRepository {
    suspend fun popularMovies(page: Int?): Flow<Either<Failure, List<Movie>>>
}

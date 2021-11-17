package com.backbase.assignment.features.movies.data.repository

import com.backbase.assignment.core.data.remote.api.MovieApi
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
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
class PopularMoviesRepository @Inject constructor(
    private val apiService: MovieApi
) : IPopularMoviesRepository {

    /**
     *  fetch popular movies data and emit success and failure response
     */
    override suspend fun popularMovies(page: Int?): Flow<Either<Failure, List<Movie>>> =
        flow {
            val response =
                apiService.getPopularMovies(page = page.toString())
            emit(
                when (response.isSuccessful) {
                    true -> {
                        response.body()?.let { it ->
                            Either.Right(it.results.map { a -> a.toDomainObject() })
                        } ?: Either.Left(Failure.DataError)
                    }
                    false -> Either.Left(Failure.ServerError)
                }
            )
        }
}

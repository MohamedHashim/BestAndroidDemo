package com.backbase.assignment.features.movies.data.repository

import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.data.remote.api.MovieApi
import com.backbase.assignment.features.movies.domain.irepository.INowPlayingMoviesRepository
import com.backbase.assignment.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

@Singleton
class NowPlayingMoviesRepository @Inject constructor(
    private val apiService: MovieApi
) : INowPlayingMoviesRepository {

    override suspend fun nowPlayingMovies(): Flow<Either<Failure, List<Movie>>> =
        flow {
        }
}

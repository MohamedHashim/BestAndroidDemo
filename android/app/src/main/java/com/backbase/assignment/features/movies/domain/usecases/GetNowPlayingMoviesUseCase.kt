package com.backbase.assignment.features.movies.domain.usecases

import com.backbase.assignment.core.baseinteractor.BaseUseCase
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.irepository.INowPlayingMoviesRepository
import com.backbase.assignment.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

class GetNowPlayingMoviesUseCase @Inject constructor(private val repository: INowPlayingMoviesRepository) :
    BaseUseCase<List<String>?, List<Movie>>() {

    override suspend fun run(params: List<String>?): Flow<Either<Failure, List<Movie>>> = flow {}
}

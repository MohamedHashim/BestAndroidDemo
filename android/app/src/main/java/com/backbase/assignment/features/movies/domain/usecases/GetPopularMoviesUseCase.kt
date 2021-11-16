package com.backbase.assignment.features.movies.domain.usecases

import com.backbase.assignment.core.baseinteractor.BaseUseCase
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.irepository.IPopularMoviesRepository
import com.backbase.assignment.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

class GetPopularMoviesUseCase @Inject constructor(private val repository: IPopularMoviesRepository) :
    BaseUseCase<Int, List<Movie>>() {

    override suspend fun run(params: Int?): Flow<Either<Failure, List<Movie>>> {

        return repository.popularMovies(params)
    }
}

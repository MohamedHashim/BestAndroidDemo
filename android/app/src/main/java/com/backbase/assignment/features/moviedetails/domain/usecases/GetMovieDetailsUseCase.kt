package com.backbase.assignment.features.moviedetails.domain.usecases

import com.backbase.assignment.core.baseinteractor.BaseUseCase
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.domain.irepository.IMovieDetailsRepository
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

class GetMovieDetailsUseCase @Inject constructor(private val repository: IMovieDetailsRepository) :
    BaseUseCase<Int, MovieDetails>() {

    override suspend fun run(params: Int?): Flow<Either<Failure, MovieDetails>> {

        return repository.movieDetails(params!!)
    }
}

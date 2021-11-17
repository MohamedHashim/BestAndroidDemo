package com.backbase.assignment.features.moviedetails.domain.usescases

import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.data.remote.model.BelongsToCollectionResponse
import com.backbase.assignment.features.moviedetails.domain.irepository.IMovieDetailsRepository
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import com.backbase.assignment.features.moviedetails.domain.usecases.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@ExperimentalCoroutinesApi
class MovieDetailsUseCaseTest : UnitTest() {
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @MockK
    private lateinit var movieDetailsRepository: IMovieDetailsRepository

    private val mockedId = 1234

    private val mockedBelongsToCollection =
        BelongsToCollectionResponse("/srYya1ZlI97Au4jUYAktDe3avyA.jpg")

    private val mockedMovieDetails = MovieDetails(
        mockedBelongsToCollection,
        runtime = 123,
        title = "End Game",
        overview = "A botched store robbery places Wonder Woman in a global battle against a powerful and mysterious ancient force that puts her powers in jeopardy.",
        release_date = "10-12-2020"
    )

    @Before
    fun setUp() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieDetailsRepository)
    }

    @Test
    fun `should call movieDetails from repository`() = runBlockingTest {
        coEvery { movieDetailsRepository.movieDetails(mockedId) } returns flow {
            emit(
                Either.Right(mockedMovieDetails)
            )
        }
        getMovieDetailsUseCase.run(mockedId)
        coVerify(exactly = 1) { movieDetailsRepository.movieDetails(mockedId) }
    }
}

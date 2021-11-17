package com.backbase.assignment.features.moviedetails.data.repository

import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.data.remote.api.MovieDetailsApi
import com.backbase.assignment.features.moviedetails.data.remote.model.BelongsToCollectionResponse
import com.backbase.assignment.features.moviedetails.data.remote.model.MovieDetailsResponse
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@ExperimentalCoroutinesApi
class MovieDetailsRepositoryTest : UnitTest() {

    private lateinit var movieDetailsRepository: MovieDetailsRepository

    private lateinit var movieDetailsResponse: MovieDetailsResponse

    @MockK
    private lateinit var apiService: MovieDetailsApi

    @MockK
    private lateinit var moviesResponse: Response<MovieDetailsResponse>

    private val mockedId = 464052
    private val mockedBelongsToCollection =
        BelongsToCollectionResponse("/srYya1ZlI97Au4jUYAktDe3avyA.jpg")

    @Before
    fun setUp() {
        movieDetailsRepository = MovieDetailsRepository(apiService)
    }

    @Test
    fun `getMovieDetails with null responseBody should return data error`() = runBlockingTest {
        every { moviesResponse.body() } returns null
        every { moviesResponse.isSuccessful } returns true
        coEvery { apiService.getMovieDetails(id = mockedId) } returns moviesResponse

        val movies = movieDetailsRepository.movieDetails(mockedId)
        movies.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `getMovieDetails should return server error when response is not successful`() =
        runBlockingTest {
            every { moviesResponse.isSuccessful } returns false
            coEvery { apiService.getMovieDetails(id = mockedId) } returns moviesResponse

            val movies = movieDetailsRepository.movieDetails(mockedId)
            movies.collect { a ->
                Truth.assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @Test
    fun `getMovieDetails should get movie details`() = runBlockingTest {
        movieDetailsResponse = MovieDetailsResponse(
            mockedBelongsToCollection,
            runtime = 123,
            title = "End Game",
            overview = "A botched store robbery places Wonder Woman in a global battle against a powerful and mysterious ancient force that puts her powers in jeopardy.",
            release_date = "10-12-2020",

        )
        every { moviesResponse.body() } returns movieDetailsResponse
        every { moviesResponse.isSuccessful } returns true
        coEvery { apiService.getMovieDetails(id = mockedId) } returns moviesResponse

        val movies = movieDetailsRepository.movieDetails(mockedId)
        movies.collect { a ->
            Truth.assertThat(a)
                .isEqualTo(
                    Either.Right(
                        movieDetailsResponse.toDomainObject()
                    )
                )
        }
    }
}

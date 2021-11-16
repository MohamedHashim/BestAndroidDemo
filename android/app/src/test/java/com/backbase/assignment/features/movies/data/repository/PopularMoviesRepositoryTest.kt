package com.backbase.assignment.features.movies.data.repository

import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.data.remote.api.MovieApi
import com.backbase.assignment.features.movies.data.remote.model.MovieResponse
import com.backbase.assignment.features.movies.data.remote.model.PopularMovieResponse
import com.google.common.truth.Truth.assertThat
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
class PopularMoviesRepositoryTest : UnitTest() {

    private lateinit var popularMoviesRepository: PopularMoviesRepository

    private lateinit var popularMovieResponse: PopularMovieResponse

    @MockK
    private lateinit var apiService: MovieApi

    @MockK
    private lateinit var moviesResponse: Response<PopularMovieResponse>

    @Before
    fun setUp() {
        popularMoviesRepository = PopularMoviesRepository(apiService)
    }

    @Test
    fun `getPopularMovies with null responseBody should return data error`() = runBlockingTest {
        every { moviesResponse.body() } returns null
        every { moviesResponse.isSuccessful } returns true
        coEvery { apiService.getPopularMovies() } returns moviesResponse

        val movies = popularMoviesRepository.popularMovies()
        movies.collect { a ->
            assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `getPopularMovies should return server error when response is not successful`() =
        runBlockingTest {
            every { moviesResponse.isSuccessful } returns false
            coEvery { apiService.getPopularMovies() } returns moviesResponse

            val movies = popularMoviesRepository.popularMovies()
            movies.collect { a ->
                assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @Test
    fun `getPopularMovies should get popular movies list`() = runBlockingTest {
        popularMovieResponse = PopularMovieResponse(
            1,
            results = listOf(
                MovieResponse(
                    id = 1223,
                    title = "End Game",
                    vote_average = 7.7f,
                    release_date = "10-12-2020",
                    poster_path = "/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"
                )
            ),
            10, 180
        )
        every { moviesResponse.body() } returns popularMovieResponse
        every { moviesResponse.isSuccessful } returns true
        coEvery { apiService.getPopularMovies() } returns moviesResponse

        val movies = popularMoviesRepository.popularMovies()
        movies.collect { a ->
            assertThat(a).isEqualTo(Either.Right(popularMovieResponse.results.map { it.toDomainObject() }))
        }
    }
}

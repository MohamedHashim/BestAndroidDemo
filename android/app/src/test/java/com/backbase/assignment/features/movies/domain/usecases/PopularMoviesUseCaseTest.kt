package com.backbase.assignment.features.movies.domain.usecases

import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.irepository.IPopularMoviesRepository
import com.backbase.assignment.features.movies.domain.model.Movie
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
class PopularMoviesUseCaseTest : UnitTest() {
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @MockK
    private lateinit var popularMoviesRepository: IPopularMoviesRepository

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(popularMoviesRepository)
    }

    @Test
    fun `should call popularMovies from repository`() = runBlockingTest {
        coEvery { popularMoviesRepository.popularMovies(1) } returns flow {
            emit(
                Either.Right(emptyList<Movie>())
            )
        }
        getPopularMoviesUseCase.run(1)
        coVerify(exactly = 1) { popularMoviesRepository.popularMovies(1) }
    }
}

package com.backbase.assignment.features.movies.domain.usecases

import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.irepository.INowPlayingMoviesRepository
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
 * Created by Mohamed Hashim on 15/11/2021.
 */

@ExperimentalCoroutinesApi
class GetNowPlayingMoviesUseCaseTest : UnitTest() {
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    @MockK
    private lateinit var nowPlayingMoviesRepository: INowPlayingMoviesRepository

    @Before
    fun setUp() {
        getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(nowPlayingMoviesRepository)
    }

    @Test
    fun `should call nowPlayingMovies from repository`() = runBlockingTest {
        coEvery { nowPlayingMoviesRepository.nowPlayingMovies() } returns flow {
            emit(
                Either.Right(emptyList<Movie>())
            )
        }
        getNowPlayingMoviesUseCase.run()
        coVerify(exactly = 1) { nowPlayingMoviesRepository.nowPlayingMovies() }
    }
}

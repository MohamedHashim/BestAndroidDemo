package com.backbase.assignment.features.movies.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.movies.domain.model.Movie
import com.backbase.assignment.features.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.backbase.assignment.features.movies.presentation.mapper.toPresentation
import com.backbase.assignment.features.movies.presentation.viewmodel.MoviesViewModel
import com.backbase.assignment.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MoviesViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    private lateinit var application: Application

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movies: List<Movie>

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()

        movies = listOf(
            Movie(
                id = 123,
                "End Game",
                5.6f,
                "29-12-2021",
                "/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"
            )
        )
        moviesViewModel =
            MoviesViewModel(application, getNowPlayingMoviesUseCase)
    }

    @Test
    fun `getNowPlayingMovies should return movies list on success`() {
        every { getNowPlayingMoviesUseCase(any(), onResult = any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Right(movies))
        }
        moviesViewModel.getNowPlayingMovies()

        val res = moviesViewModel.nowPlayingMoviesView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isLoading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.movies).isEqualTo(movies.map { it.toPresentation() })
    }

    @Test
    fun `getNowPlayingMovies should show empty view when list of movies is empty`() {
        every { getNowPlayingMoviesUseCase(any(), onResult = any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Right(emptyList()))
        }
        moviesViewModel.getNowPlayingMovies()

        val res = moviesViewModel.nowPlayingMoviesView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).containsMatch("There is no data to show")
        assertThat(res.isLoading).isFalse()
        assertThat(res.isEmpty).isTrue()
        assertThat(res.movies).isNull()
    }

    @Test
    fun `getNowPlayingMovies should show error when a server error occur`() {
        every { getNowPlayingMoviesUseCase(any(), onResult = any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        moviesViewModel.getNowPlayingMovies()

        val res = moviesViewModel.nowPlayingMoviesView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).containsMatch("Server Error : Could not retrieve data from servers")
        assertThat(res.isLoading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.movies).isNull()
    }

    @Test
    fun `getNowPlayingMovies should show error when a data error occur`() {
        every { getNowPlayingMoviesUseCase(any(), onResult = any()) }.answers {
            thirdArg<(Either<Failure, List<Movie>>) -> Unit>()(Either.Left(Failure.DataError))
        }
        moviesViewModel.getNowPlayingMovies()

        val res = moviesViewModel.nowPlayingMoviesView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).containsMatch("Data Error : There is no data to show")
        assertThat(res.isLoading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.movies).isNull()
    }
}

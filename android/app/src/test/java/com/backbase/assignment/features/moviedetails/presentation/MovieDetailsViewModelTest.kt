package com.backbase.assignment.features.moviedetails.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backbase.assignment.UnitTest
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.core.functional.Either
import com.backbase.assignment.features.moviedetails.data.remote.model.BelongsToCollectionResponse
import com.backbase.assignment.features.moviedetails.data.remote.model.Genre
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import com.backbase.assignment.features.moviedetails.domain.usecases.GetMovieDetailsUseCase
import com.backbase.assignment.features.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import com.backbase.assignment.getOrAwaitValueTest
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var application: Application

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private val id = 1223
    private val belongsToCollectionResponse =
        BelongsToCollectionResponse(poster_path = "/d5NXSklXo0qyIYkgV94XAgMIckC.jpg")

    private val genresList = listOf(
        Genre("Drama"),
        Genre("Action"),
        Genre("Adventure"),
        Genre("Fantasy")
    )

    private val movieDetails = MovieDetails(
        belongsToCollectionResponse,
        runtime = 123,
        title = "End Game",
        overview = "A botched store robbery places Wonder Woman in a global battle against a powerful and mysterious ancient force that puts her powers in jeopardy.",
        release_date = "10-12-2020",
        genres = genresList
    )

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()

        movieDetailsViewModel =
            MovieDetailsViewModel(application, getMovieDetailsUseCase)
    }

    /**
     * Testing MovieDetails feature
     */

    @Test
    fun `getMovieDetails should return movie details on success`() {
        every { getMovieDetailsUseCase(any(), onResult = any(), params = any()) }.answers {
            thirdArg<(Either<Failure, MovieDetails>) -> Unit>()(Either.Right(movieDetails))
        }
        movieDetailsViewModel.getMovieDetails(id)

        val res = movieDetailsViewModel.movieDetailsView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.isLoading).isFalse()
        Truth.assertThat(res.movie?.poster_path)
            .isEqualTo(movieDetails.belongs_to_collection.poster_path)
        Truth.assertThat(res.movie?.genres).isEqualTo(movieDetails.genres)
        Truth.assertThat(res.movie?.title).isEqualTo(movieDetails.title)
        Truth.assertThat(res.movie?.runtime).isEqualTo(movieDetails.runtime)
        Truth.assertThat(res.movie?.overview).isEqualTo(movieDetails.overview)
        Truth.assertThat(res.movie?.release_date).isEqualTo(movieDetails.release_date)
    }

    @Test
    fun `getMovieDetails should show error when a server error occur`() {
        every { getMovieDetailsUseCase(any(), onResult = any(), params = any()) }.answers {
            thirdArg<(Either<Failure, MovieDetails>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        movieDetailsViewModel.getMovieDetails(id)

        val res = movieDetailsViewModel.movieDetailsView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.errorMessage)
            .containsMatch("Server Error : Could not retrieve data from servers")
        Truth.assertThat(res.isLoading).isFalse()
        Truth.assertThat(res.movie).isNull()
    }

    @Test
    fun `getMovieDetails should show error when a data error occur`() {
        every { getMovieDetailsUseCase(any(), onResult = any(), params = any()) }.answers {
            thirdArg<(Either<Failure, MovieDetails>) -> Unit>()(Either.Left(Failure.DataError))
        }
        movieDetailsViewModel.getMovieDetails(id)

        val res = movieDetailsViewModel.movieDetailsView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.errorMessage).containsMatch("Data Error : There is no data to show")
        Truth.assertThat(res.isLoading).isFalse()
        Truth.assertThat(res.movie).isNull()
    }
}

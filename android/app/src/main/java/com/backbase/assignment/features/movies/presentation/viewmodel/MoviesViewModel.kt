package com.backbase.assignment.features.movies.presentation.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.backbase.assignment.R
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.features.movies.domain.model.Movie
import com.backbase.assignment.features.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.backbase.assignment.features.movies.domain.usecases.GetPopularMoviesUseCase
import com.backbase.assignment.features.movies.presentation.mapper.toPopularMoviePresentation
import com.backbase.assignment.features.movies.presentation.mapper.toPresentation
import com.backbase.assignment.features.movies.presentation.model.state.NowPlayingMovieView
import com.backbase.assignment.features.movies.presentation.model.state.PopularMovieView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    application: Application,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : AndroidViewModel(application) {

    private val job = Job()
    private val resources = application.resources

    private val _nowPlayingMoviesView = MutableLiveData<NowPlayingMovieView>()
    val nowPlayingMoviesView: LiveData<NowPlayingMovieView>
        get() = _nowPlayingMoviesView

    private val _popularMoviesView = MutableLiveData<PopularMovieView>()
    val popularMoviesView: LiveData<PopularMovieView>

    private val _page = MutableLiveData<Int>()
    private val page: LiveData<Int>
        get() = _page

    init {
        getNowPlayingMovies()
        popularMoviesView = page.switchMap {
            _popularMoviesView.apply { getPopularMovies(it) }
        }
    }

    /**
     * get now playing movies with success and error handling
     */
    fun getNowPlayingMovies() {
        _nowPlayingMoviesView.value = NowPlayingMovieView(isLoading = true)
        getNowPlayingMoviesUseCase(job) {
            it.fold(::handleMoviesFailure, ::handleMoviesSuccess)
        }
    }

    /**
     * now playing movies success handling
     */
    private fun handleMoviesSuccess(movies: List<Movie>) {

        if (movies.isEmpty()) {
            _nowPlayingMoviesView.value = NowPlayingMovieView(
                isEmpty = true,
                errorMessage = resources.getString(R.string.empty_error)
            )
        } else {
            _nowPlayingMoviesView.postValue(
                NowPlayingMovieView(movies = movies.map { it.toPresentation() })
            )
        }
    }

    /**
     * now playing movies error handling
     */
    @Suppress("UNUSED_PARAMETER")
    private fun handleMoviesFailure(failure: Failure) {

        when (failure) {
            Failure.DataError -> {
                _nowPlayingMoviesView.value =
                    NowPlayingMovieView(
                        errorMessage = resources.getString(
                            R.string.data_error
                        )
                    )
            }
            Failure.ServerError -> {
                _nowPlayingMoviesView.value =
                    NowPlayingMovieView(
                        errorMessage = resources.getString(
                            R.string.server_error
                        )
                    )
            }
        }
    }

    /**
     * post new page value for popular movies
     */
    fun postPage(page: Int) {
        this._page.value = page
    }

    /**
     * get popular movies with success and error handling
     */
    fun getPopularMovies(page: Int = 1) {
        _popularMoviesView.value = PopularMovieView(isLoading = true)
        getPopularMoviesUseCase(job, params = page) {
            it.fold(::handlePopularMoviesFailure, ::handlePopularMoviesSuccess)
        }
    }

    /**
     * popular movies success handling
     */
    private fun handlePopularMoviesSuccess(movies: List<Movie>) {

        if (movies.isEmpty()) {
            _popularMoviesView.value = PopularMovieView(
                isEmpty = true,
                errorMessage = resources.getString(R.string.empty_error)
            )
        } else {
            _popularMoviesView.postValue(
                PopularMovieView(movies = movies.map { it.toPopularMoviePresentation() })
            )
        }
    }

    /**
     * popular movies error handling
     */
    @Suppress("UNUSED_PARAMETER")
    private fun handlePopularMoviesFailure(failure: Failure) {

        when (failure) {
            Failure.DataError -> {
                _popularMoviesView.value =
                    PopularMovieView(
                        errorMessage = resources.getString(
                            R.string.data_error
                        )
                    )
            }
            Failure.ServerError -> {
                _popularMoviesView.value =
                    PopularMovieView(
                        errorMessage = resources.getString(
                            R.string.server_error
                        )
                    )
            }
        }
    }

    /**
     * clear job to avoid memory leak
     */
    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    /**
     * companion object to send data bundle
     */
    companion object {
        private const val movieIKey = "movieId"
        fun createArguments(movieId: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(movieIKey, movieId)
            return bundle
        }
    }
}

package com.backbase.assignment.features.movies.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.R
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.features.movies.domain.model.Movie
import com.backbase.assignment.features.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.backbase.assignment.features.movies.presentation.mapper.toPresentation
import com.backbase.assignment.features.movies.presentation.model.state.NowPlayingMovieView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    application: Application,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) :
    AndroidViewModel(application) {

    private val job = Job()
    private val resources = getApplication<Application>().applicationContext.resources

    private val _nowPlayingMoviesView = MutableLiveData<NowPlayingMovieView>()
    val nowPlayingMoviesView: LiveData<NowPlayingMovieView>
        get() = _nowPlayingMoviesView

    fun getNowPlayingMovies() {
        _nowPlayingMoviesView.value = NowPlayingMovieView(isLoading = true)
        getNowPlayingMoviesUseCase(job) {
            it.fold(::handleMoviesFailure, ::handleMoviesSuccess)
        }
    }

    private fun handleMoviesSuccess(movies: List<Movie>) {

        if (movies.isEmpty()) {
            _nowPlayingMoviesView.value = NowPlayingMovieView(isEmpty = true)
        } else {
            _nowPlayingMoviesView.value =
                NowPlayingMovieView(movies = movies.map { it.toPresentation() })
        }
    }

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

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

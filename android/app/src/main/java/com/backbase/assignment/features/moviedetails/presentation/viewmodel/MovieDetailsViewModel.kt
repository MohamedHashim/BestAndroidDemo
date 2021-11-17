package com.backbase.assignment.features.moviedetails.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.R
import com.backbase.assignment.core.exceptions.Failure
import com.backbase.assignment.features.moviedetails.domain.model.MovieDetails
import com.backbase.assignment.features.moviedetails.domain.usecases.GetMovieDetailsUseCase
import com.backbase.assignment.features.moviedetails.presentation.mapper.toPresentation
import com.backbase.assignment.features.moviedetails.presentation.model.state.MovieDetailsView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    application: Application,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : AndroidViewModel(application) {

    private val job = Job()
    private val resources = application.resources

    private val _movieDetailsView = MutableLiveData<MovieDetailsView>()
    val movieDetailsView: LiveData<MovieDetailsView>
        get() = _movieDetailsView

    /**
     * get movie details with success and error handling
     */
    fun getMovieDetails(id: Int) {
        _movieDetailsView.value = MovieDetailsView(isLoading = true)
        getMovieDetailsUseCase(job, params = id) {
            it.fold(::handleMovieDetailsFailure, ::handleMovieDetailsSuccess)
        }
    }

    /**
     * movie details success handling
     */
    private fun handleMovieDetailsSuccess(movie: MovieDetails) {

        _movieDetailsView.postValue(
            MovieDetailsView(movie = movie.toPresentation())
        )
    }

    /**
     * movies details error handling
     */
    @Suppress("UNUSED_PARAMETER")
    private fun handleMovieDetailsFailure(failure: Failure) {

        when (failure) {
            Failure.DataError -> {
                _movieDetailsView.value =
                    MovieDetailsView(
                        errorMessage = resources.getString(
                            R.string.data_error
                        )
                    )
            }
            Failure.ServerError -> {
                _movieDetailsView.value =
                    MovieDetailsView(
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
}

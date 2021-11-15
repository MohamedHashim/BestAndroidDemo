package com.backbase.assignment.features.movies.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.features.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.backbase.assignment.features.movies.presentation.model.state.NowPlayingMovieView
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _nowPlayingMoviesView = MutableLiveData<NowPlayingMovieView>()
    val nowPlayingMoviesView: LiveData<NowPlayingMovieView>
        get() = _nowPlayingMoviesView

    fun getNowPlayingMovies() {
    }
}

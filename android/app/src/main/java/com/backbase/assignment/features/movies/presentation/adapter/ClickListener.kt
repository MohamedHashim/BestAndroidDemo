package com.backbase.assignment.features.movies.presentation.adapter

import android.view.View

/**
 * Created by Mohamed Hashim on 17/11/2021.
 */

interface ClickListener {
    fun onPopularMovieClick(view: View, movieId: Int)
    fun onNowPlayingMovieClick(movieId: Int)
}

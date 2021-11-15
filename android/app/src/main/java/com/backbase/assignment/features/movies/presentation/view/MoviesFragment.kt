package com.backbase.assignment.features.movies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.backbase.assignment.R
import com.backbase.assignment.features.movies.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moviesViewModel.getNowPlayingMovies()
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }
}

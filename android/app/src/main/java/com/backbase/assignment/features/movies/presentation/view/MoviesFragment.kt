package com.backbase.assignment.features.movies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.assignment.databinding.FragmentMoviesBinding
import com.backbase.assignment.features.movies.presentation.adapter.NowPlayingMoviesAdapter
import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation
import com.backbase.assignment.features.movies.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding.moviesViewModel = moviesViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        moviesViewModel.getNowPlayingMovies()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNowPlayingMoviesView()
    }

    /**
     * Observe now playing view state
     */
    private fun observeNowPlayingMoviesView() {
        this.moviesViewModel.nowPlayingMoviesView.observe(
            viewLifecycleOwner,
            { nowPlayingMoviesView ->
                if (nowPlayingMoviesView.movies != null)
                    setupNowPlayingMoviesAdapter(nowPlayingMoviesView.movies)
            }
        )
    }

    /**
     * Initialize now playing movies adapter
     */
    private fun setupNowPlayingMoviesAdapter(movies: List<NowPlayingMoviePresentation>?) {
        nowPlayingMoviesAdapter =
            NowPlayingMoviesAdapter(movies)
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvNowPlayingMovies.layoutManager = linearLayoutManager
        binding.rvNowPlayingMovies.adapter = nowPlayingMoviesAdapter
    }

    /**
     * clear binding to avoid memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

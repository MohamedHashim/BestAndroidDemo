package com.backbase.assignment.features.movies.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.assignment.databinding.FragmentMoviesBinding
import com.backbase.assignment.features.movies.presentation.adapter.MoviesAdapter
import com.backbase.assignment.features.movies.presentation.adapter.MoviesViewHolder
import com.backbase.assignment.features.movies.presentation.adapter.NowPlayingMoviesAdapter
import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation
import com.backbase.assignment.features.movies.presentation.model.PopularMoviePresentation
import com.backbase.assignment.features.movies.presentation.viewmodel.MoviesViewModel
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesViewHolder.Delegate {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter
    private val adapterMovieList = MoviesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding.moviesViewModel = moviesViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        moviesViewModel.getNowPlayingMovies()
        moviesViewModel.getPopularMovies()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvPopular()
        observeNowPlayingMoviesView()
        observePopularMoviesView()
    }

    /**
     * Observe now playing movie view state
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
     * Observe popular movie view state
     */
    private fun observePopularMoviesView() {
        this.moviesViewModel.popularMoviesView.observe(
            viewLifecycleOwner,
            { popularMoviesView ->
                if (popularMoviesView.movies != null) {
                    setupPopularMoviesAdapter(popularMoviesView.movies)
                }
            }
        )
    }

    /**
     * Initialize popular movies adapter
     */
    private fun setupPopularMoviesAdapter(movies: List<PopularMoviePresentation>?) {
        adapterMovieList.addMovieList(movies!!)
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvPopularMovies.layoutManager = linearLayoutManager
        binding.rvPopularMovies.adapter = adapterMovieList
    }

    /**
     * Init popular movies recyclerview paginator
     */
    private fun initRvPopular() {
        RecyclerViewPaginator(
            recyclerView = binding.rvPopularMovies,
            isLoading = { false },
            loadMore = { moviesViewModel.getPopularMovies(it) },
            onLast = { false }
        ).apply {
            threshold = 5
            currentPage = 1
        }
    }

    /**
     * clear binding to avoid memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(view: View, movie: PopularMoviePresentation) {
        Toast.makeText(context, movie.title, Toast.LENGTH_LONG).show()
    }
}

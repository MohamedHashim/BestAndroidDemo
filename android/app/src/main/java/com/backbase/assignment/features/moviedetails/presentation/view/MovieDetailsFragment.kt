package com.backbase.assignment.features.moviedetails.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentMovieDetailBinding
import com.backbase.assignment.features.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Hashim on 17/11/2021.
 */

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().get(getString(R.string.movie_key)) as Int
        viewModel.getMovieDetails(movie_id = movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovieDetailsView()
    }

    /**
     * Observe movie details view state
     */
    private fun observeMovieDetailsView() {
        this.viewModel.movieDetailsView.observe(
            viewLifecycleOwner,
            {
                if (it.movie != null)
                    Toast.makeText(context, it.movie.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }
}

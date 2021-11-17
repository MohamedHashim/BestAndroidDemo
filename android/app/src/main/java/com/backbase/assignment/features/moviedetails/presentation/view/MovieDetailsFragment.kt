package com.backbase.assignment.features.moviedetails.presentation.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentMovieDetailBinding
import com.backbase.assignment.features.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Hashim on 17/11/2021.
 */

@AndroidEntryPoint
class MovieDetailsFragment : DialogFragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movieId: Int = 0

    override fun onStart() {
        super.onStart()
        setWindowParams()
    }

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
        binding.view = this

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
                if (it.movie != null) {
                    binding.movie = it.movie
                    it.movie.genres.forEach { genreName ->
                        createChip(genreName.name)
                    }
                }
            }
        )
    }

    /**
     * Set fragment background as transparent
     */
    private fun setWindowParams() {
        val window: Window? = dialog?.window
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    /**
     * Create chips in chips group
     */
    private fun createChip(name: String) {
        val chip = Chip(this.context)
        chip.text = name
        chip.setTextAppearance(R.style.ChipTextAppearance)
        chip.setChipBackgroundColorResource(R.color.white)
        chip.chipCornerRadius = 12f
        binding.chipsGroup.addView(chip)
    }

    /**
     * back button action
     */
    fun clickBackBtn() {
        findNavController().navigateUp()
    }
}

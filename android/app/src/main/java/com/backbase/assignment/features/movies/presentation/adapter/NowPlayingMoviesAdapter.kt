package com.backbase.assignment.features.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemNowPlayingMovieBinding
import com.backbase.assignment.features.movies.presentation.model.NowPlayingMoviePresentation

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

class NowPlayingMoviesAdapter(
    private val movies: List<NowPlayingMoviePresentation>?,
    private val delegate: ClickListener
) :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNowPlayingMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListViewModel = movies?.get(position)
        holder.bind(myListViewModel)
    }

    override fun getItemCount(): Int {
        return movies?.size!!
    }

    class ViewHolder(
        private val binding: ItemNowPlayingMovieBinding,
        private val delegate: ClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: NowPlayingMoviePresentation?) {
            binding.movie = movie
            binding.root.setOnClickListener { delegate.onNowPlayingMovieClick(movie?.id!!) }
        }
    }
}

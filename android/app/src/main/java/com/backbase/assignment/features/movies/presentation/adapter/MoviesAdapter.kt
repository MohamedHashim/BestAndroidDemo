package com.backbase.assignment.features.movies.presentation.adapter

import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MovieItemBinding
import com.backbase.assignment.features.movies.presentation.model.PopularMoviePresentation
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

class MoviesAdapter(
    private val delegate: MoviesViewHolder.Delegate
) : BaseAdapter() {

    init {
        addSection(ArrayList<PopularMoviePresentation>())
    }

    fun addMovieList(movies: List<PopularMoviePresentation>) {
        val section = sections()[0]
        section.addAll(movies)
        notifyItemRangeInserted(section.size - movies.size + 1, movies.size)
    }

    override fun layout(sectionRow: SectionRow) = R.layout.movie_item

    override fun viewHolder(layout: Int, view: View) = MoviesViewHolder(view, delegate)
}

class MoviesViewHolder(
    view: View,
    private val delegate: Delegate
) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(view: View, movie: PopularMoviePresentation)
    }

    private lateinit var movie: PopularMoviePresentation
    private val binding by bindings<MovieItemBinding>(view)

    override fun bindData(data: Any) {
        if (data is PopularMoviePresentation) {
            movie = data
            binding.apply {
                ViewCompat.setTransitionName(binding.itemMovieContainer, data.title)
                movie = data
                executePendingBindings()
            }
        }
    }

    override fun onClick(v: View?) = delegate.onItemClick(binding.itemMovieContainer, movie)

    override fun onLongClick(v: View?): Boolean {
        Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
        return true
    }
}

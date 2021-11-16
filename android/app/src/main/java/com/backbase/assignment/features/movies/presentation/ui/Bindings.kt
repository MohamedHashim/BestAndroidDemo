package com.backbase.assignment.features.movies.presentation.ui

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.backbase.assignment.R
import com.backbase.assignment.core.data.EndPoint
import com.bumptech.glide.Glide

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

/**
 * extension function to set poster image from url into imageview
 */
@BindingAdapter("bindingPosterUrl")
fun ImageView.bindingPosterUrl(path: String?) {
    path?.let {
        this.clipToOutline = true
        Glide.with(this.context)
            .load(EndPoint.getPosterPath(it))
            .error(ContextCompat.getDrawable(this.context, R.drawable.ic_not_found))
            .into(this)
    }
}

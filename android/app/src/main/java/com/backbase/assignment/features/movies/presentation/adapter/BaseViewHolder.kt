package com.backbase.assignment.features.movies.presentation.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mohamed Hashim on 16/11/2021.
 */

@Suppress("unused", "LeakingThis")
abstract class BaseViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view),
    View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    /** binds data to the view holder class. */
    @Throws(Exception::class)
    abstract fun bindData(data: Any)

    /** gets the view of the [RecyclerView.ViewHolder]. */
    fun view(): View {
        return view
    }

    /** gets the context. */
    fun context(): Context {
        return view.context
    }
}

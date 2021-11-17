package com.backbase.assignment.core.di

import com.backbase.assignment.features.moviedetails.data.repository.MovieDetailsRepository
import com.backbase.assignment.features.moviedetails.domain.irepository.IMovieDetailsRepository
import com.backbase.assignment.features.movies.data.repository.NowPlayingMoviesRepository
import com.backbase.assignment.features.movies.data.repository.PopularMoviesRepository
import com.backbase.assignment.features.movies.domain.irepository.INowPlayingMoviesRepository
import com.backbase.assignment.features.movies.domain.irepository.IPopularMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

/**
 *  bind repositories to the domain layer
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    /**
     *  bind NowPlayingMoviesRepository to the domain layer
     */
    @Binds
    abstract fun bindNowPlayingMoviesRepository(repo: NowPlayingMoviesRepository): INowPlayingMoviesRepository

    /**
     *  bind PopularMoviesRepository to the domain layer
     */
    @Binds
    abstract fun bindPopularMoviesRepository(repo: PopularMoviesRepository): IPopularMoviesRepository

    /**
     *  bind MovieDetailsRepository to the domain layer
     */
    @Binds
    abstract fun bindMovieDetailsRepository(repo: MovieDetailsRepository): IMovieDetailsRepository
}

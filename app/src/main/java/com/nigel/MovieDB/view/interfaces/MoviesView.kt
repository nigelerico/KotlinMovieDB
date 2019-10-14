package com.nigel.MovieDB.view.interfaces

import com.nigel.MovieDB.models.Movie

interface MoviesView {

    fun onMoviesLoaded(movies: List<Movie>)

    fun onError(throwable: Throwable?)

    fun hideLoading()

}
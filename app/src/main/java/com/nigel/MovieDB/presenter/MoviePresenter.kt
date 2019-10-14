package com.nigel.MovieDB.presenter

import android.annotation.SuppressLint
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.nigel.MovieDB.VolleySingleton
import com.nigel.MovieDB.models.Movie
import com.nigel.MovieDB.models.MoviesListResponse
import com.nigel.MovieDB.shared.App
import com.nigel.MovieDB.shared.Server
import com.nigel.MovieDB.view.interfaces.MoviesView
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@SuppressLint("Registered")
class MoviePresenter : App() {

    private var moviesView: MoviesView? = null
    private var resultMovieList: ArrayList<Movie> = ArrayList()
    private lateinit var gson: Gson

    fun onViewCreated(view: MoviesView) {
        moviesView = view
    }

    fun getMovies() {
        val singleton = VolleySingleton.getInstance(App.instance)

        // Create the request and handle the response.
        val movieRequest = StringRequest(
            Request.Method.GET,
            getMovieUrl(),
            Response.Listener<String> { response ->
                gson = GsonBuilder().create()
                val moviesList: MoviesListResponse = gson.fromJson(response.toString(), MoviesListResponse::class.java)

                if (moviesList.totalResults > 0) {
                    resultMovieList.addAll(moviesList.movies)
                }
                moviesView?.onMoviesLoaded(resultMovieList)
            },

            Response.ErrorListener { error ->
                // Handle error
                error.printStackTrace()
                moviesView?.onError(error)

            })

        // Add the request to the VolleyController
        singleton.requestQueue.add(movieRequest)
    }

    private fun getMovieUrl(): String {
        return Server.MOVIE_BASE_URL + Server.MOVIE_SORT_ORDER + Server.MOVIE_API_KEY
    }
}
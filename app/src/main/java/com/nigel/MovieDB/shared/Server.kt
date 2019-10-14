package com.nigel.MovieDB.shared

class Server {
    companion object {
        const val MOVIE_BASE_URL: String = "http://api.themoviedb.org/3/movie/top_rated?"
        const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500"
        const val MOVIE_SORT_ORDER: String = "popularity.desc/popular"
        const val MOVIE_API_KEY: String = "&api_key=7e8f60e325cd06e164799af1e317d7a7"
    }
}
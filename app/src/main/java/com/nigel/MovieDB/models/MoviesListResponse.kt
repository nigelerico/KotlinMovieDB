package com.nigel.MovieDB.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoviesListResponse {

    @SerializedName("page")
    @Expose
    var page: Int = 0

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0

    @SerializedName("results")
    @Expose
    var movies: List<Movie> = emptyList()

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0

}
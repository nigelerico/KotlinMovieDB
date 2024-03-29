package com.nigel.MovieDB.view.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.nigel.MovieDB.R
import com.nigel.MovieDB.models.Movie
import com.nigel.MovieDB.presenter.MoviePresenter
import com.nigel.MovieDB.view.adapters.MovieListAdapter
import com.nigel.MovieDB.view.interfaces.MoviesView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : BaseActivity(), MoviesView, SwipeRefreshLayout.OnRefreshListener {

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById<SwipeRefreshLayout>(R.id.swipe_container)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.progress_bar)
    }

    private val statusTextView: TextView by lazy {
        findViewById<TextView>(R.id.status_text_view)
    }

    private val movieAdapter = MovieListAdapter()

    private val moviePresenter = MoviePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        prepareSwipeRefreshLayout()
        prepareRecyclerView()
        prepareAdapter()

        moviePresenter.onViewCreated(this)
        moviePresenter.getMovies()
    }

    override fun onMoviesLoaded(movies: List<Movie>) {
        hideLoading()
        swipeRefreshLayout.isRefreshing = false
        if (movies.isEmpty()) {
            statusTextView.text = getString(R.string.list_empty_error)
            return
        }
        statusTextView.text = null
        movieAdapter.setDataSource(movies)
    }

    override fun onError(throwable: Throwable?) {
        hideLoading()
        if (throwable is IOException) {
            statusTextView.text = getString(R.string.connection_error)
        } else {
            statusTextView.setText(R.string.list_empty_error)
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
    }

    override fun onRefresh() {
        movieAdapter.clearDataSource()
        moviePresenter.getMovies()
    }

    private fun prepareSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun prepareRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun prepareAdapter() {
        recyclerView.adapter = movieAdapter
    }
}

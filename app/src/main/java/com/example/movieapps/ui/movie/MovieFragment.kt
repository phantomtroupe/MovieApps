package com.example.movieapps.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.R
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), NetworkListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        movieViewModel.setMovies()
    }

    override fun onSuccess(message: String) {
        Log.d("Get Movie",message)
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity,resources.getString(R.string.failure),Toast.LENGTH_SHORT).show()
        Log.d("Get Movie",message)
        refresh_layout.isRefreshing=false
    }
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Movie Fragment","View Created")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("ActivityCreated","Ulala")

        movieViewModel = ViewModelProvider(this,Injection.provideViewModelFractory(this.context!!))
            .get(MovieViewModel::class.java)
        movieViewModel.networkListener = this
        initObserver()
        refresh_layout.setOnRefreshListener(this)
        refresh_layout.isRefreshing=true
        updateMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Fragment","Destroyed")
    }

    fun updateMovie(){
        movieViewModel.setMovies()
    }

    fun initObserver(){
        movieViewModel.getMovies().observe(this, Observer<MovieResponse>{
            response ->
            Log.d("Movie Response",response.results.toString())
            movieAdapter = MovieAdapter(this.context!!, response)
            rv_movies.layoutManager = LinearLayoutManager(this.context!!)
            rv_movies.adapter = movieAdapter
            refresh_layout.isRefreshing = false
        })
    }

}

package com.example.movieapps.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.Network.Routes
import com.example.movieapps.R
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.MovieAdapter
import com.example.movieapps.ui.ViewModelFactory
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieViewModel = ViewModelProvider(this,Injection.provideViewModelFractory())
            .get(MovieViewModel::class.java)
        movieViewModel.networkListener = this
        initObserver()
        refresh_layout.setOnRefreshListener(this)
        if(movieViewModel.getMovies().value?.results?.size == 0){
            refresh_layout.isRefreshing=true
            updateMovie()
        }
        Log.e("ActivityCreated","")
    }

    fun updateMovie(){
        movieViewModel.setMovies()
    }

    fun initObserver(){
        movieViewModel.getMovies().observe(this, Observer<MovieResponse>{
            response ->
            Log.d("Movie Response",response.results.toString())
            movieAdapter = MovieAdapter(this.context!!,response)
            rv_movies.layoutManager = LinearLayoutManager(this.context!!)
            rv_movies.adapter = movieAdapter
            refresh_layout.isRefreshing = false
        })
    }

}

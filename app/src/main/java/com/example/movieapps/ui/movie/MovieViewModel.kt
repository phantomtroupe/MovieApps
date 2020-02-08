package com.example.movieapps.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.data.response.movie.MovieResponse

class MovieViewModel(private val repository: MovieAppRepository) :ViewModel() {
    var networkListener:NetworkListener? = null

    fun getMovies():LiveData<MovieResponse> = repository._movies
    fun setMovies() = repository.getMovies(networkListener)
}
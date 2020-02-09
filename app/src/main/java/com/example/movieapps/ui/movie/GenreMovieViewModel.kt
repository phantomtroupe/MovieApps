package com.example.movieapps.ui.movie

import androidx.lifecycle.ViewModel
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.data.repository.MovieAppRepository

class GenreMovieViewModel(private val repository: MovieAppRepository) : ViewModel() {
    var networkListener:NetworkListener? = null

    fun getMovieGenre() = repository._genreMovie
    fun setMovieGenre() = repository.getMovieGenres(networkListener)
}
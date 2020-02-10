package com.example.movieapps.ui.movie

import androidx.lifecycle.ViewModel
import com.example.movieapps.data.repository.MovieAppRepository

class FavoriteMovieViewModel(val repository: MovieAppRepository) : ViewModel() {
    fun getFavMovie() = repository._favMovie
    fun setFavMovie() = repository.getFavoriteMovie()
}
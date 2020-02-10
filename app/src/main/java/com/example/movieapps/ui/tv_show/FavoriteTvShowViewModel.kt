package com.example.movieapps.ui.movie

import androidx.lifecycle.ViewModel
import com.example.movieapps.data.repository.MovieAppRepository

class FavoriteTvShowViewModel(val repository: MovieAppRepository) : ViewModel() {
    fun getFavTvShow() = repository._favTvShow
    fun setFavTvShow() = repository.getFavoriteTvShow()
}
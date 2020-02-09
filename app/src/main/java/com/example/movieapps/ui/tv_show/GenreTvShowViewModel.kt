package com.example.movieapps.ui.tv_show

import androidx.lifecycle.ViewModel
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.data.repository.MovieAppRepository

class GenreTvShowViewModel(private val repository: MovieAppRepository):ViewModel() {
    var networkListener: NetworkListener? = null

    fun getTvShowGenre() = repository._genreTvShow
    fun setTvShowGenre() = repository.getTvShowGenres(networkListener)
}
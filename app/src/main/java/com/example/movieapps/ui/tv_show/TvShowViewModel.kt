package com.example.movieapps.ui.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.data.response.tv_show.TvShowResponse

class TvShowViewModel(private val repository: MovieAppRepository) : ViewModel() {
    val networkListener:NetworkListener? = null

    fun getTvShow():LiveData<TvShowResponse> = repository._tvShows
    fun setTvShow() = repository.getTvShows(networkListener)
}
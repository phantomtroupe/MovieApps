package com.example.movieapps.ui.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.data.response.tv_show.TvShowResponse

class TvShowViewModel(private val repository: MovieAppRepository) : ViewModel() {
    var networkListener:NetworkListener? = null

    fun getTvShow():LiveData<ArrayList<TvShowResponse>> = repository._tvShows
    fun setTvShow(page:Int=1) = repository.getTvShows(networkListener,page)
}
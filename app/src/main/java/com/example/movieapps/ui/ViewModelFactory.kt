package com.example.movieapps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.ui.movie.MovieViewModel
import com.example.movieapps.ui.tv_show.TvShowViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: MovieAppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(TvShowViewModel::class.java)){
            return TvShowViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
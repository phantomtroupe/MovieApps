package com.example.movieapps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.ui.movie.FavoriteMovieViewModel
import com.example.movieapps.ui.movie.FavoriteTvShowViewModel
import com.example.movieapps.ui.movie.GenreMovieViewModel
import com.example.movieapps.ui.movie.MovieViewModel
import com.example.movieapps.ui.tv_show.GenreTvShowViewModel
import com.example.movieapps.ui.tv_show.TvShowViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: MovieAppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(TvShowViewModel::class.java)){
            return TvShowViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(GenreMovieViewModel::class.java)){
            return GenreMovieViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(GenreTvShowViewModel::class.java)){
            return GenreTvShowViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java)){
            return FavoriteMovieViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java)){
            return FavoriteTvShowViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
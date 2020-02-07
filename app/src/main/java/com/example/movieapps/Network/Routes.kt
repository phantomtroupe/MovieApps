package com.example.movieapps.Network

import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.tv_show.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET

interface Routes {

    @GET("discover/movie/")
    fun getMovies() : Call<MovieResponse>

    @GET("dicover/tv")
    fun getTvShow() : Call<TvShowResponse>
}
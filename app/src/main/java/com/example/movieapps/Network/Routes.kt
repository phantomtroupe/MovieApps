package com.example.movieapps.Network

import com.example.movieapps.data.response.genres.GenreResponse
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.tv_show.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Routes {

    @GET("discover/movie")
    fun getMovies(@Query("page")page:Int = 1) : Call<MovieResponse>

    @GET("discover/tv")
    fun getTvShow(@Query("page")page:Int = 1) : Call<TvShowResponse>

    @GET("genre/movie/list")
    fun getMovieGenre() : Call<GenreResponse>

    @GET("genre/tv/list")
    fun getTvShowGenre() : Call<GenreResponse>
}
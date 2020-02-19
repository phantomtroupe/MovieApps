package com.example.movieapps.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.Network.Routes
import com.example.movieapps.data.database.FavoriteDatabaseHelper
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.entity.FavoriteTvShowEntity
import com.example.movieapps.data.response.genres.GenreResponse
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.data.response.tv_show.TvShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieAppRepository(val routes: Routes, val context: Context) {
    val _movies = MutableLiveData<MovieResponse>()
    val _tvShows = MutableLiveData<TvShowResponse>()
    val _genreMovie = MutableLiveData<GenreResponse>()
    val _genreTvShow = MutableLiveData<GenreResponse>()
    val _favMovie = MutableLiveData<Array<FavoriteMovieEntity>>()
    val _favTvShow = MutableLiveData<Array<FavoriteTvShowEntity>>()


    fun getMovies(networkListener: NetworkListener?){
        routes.getMovies().enqueue(object: Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                var movieResponse = FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()
                if(movieResponse != null){
                    movieResponse.results = ArrayList(FavoriteDatabaseHelper.createDb(context).movieDao().getMovies().results)
                    _movies.value = movieResponse
                }
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    _movies.value = response.body()
                    FavoriteDatabaseHelper.createDb(context).movieDao().deleteResponse()
                    FavoriteDatabaseHelper.createDb(context).movieDao().addResponse(response.body()!!)
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                    var movieResponse = FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()
                    movieResponse.results = ArrayList(FavoriteDatabaseHelper.createDb(context).movieDao().getMovies().results)
                    _movies.value = movieResponse
                }
            }

        })
    }

    fun getTvShows(networkListener: NetworkListener?){
        routes.getTvShow().enqueue(object: Callback<TvShowResponse>{
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                var tvShowResponse = FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()
                if(tvShowResponse != null){
                    tvShowResponse.results = ArrayList(FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows().results)
                    _tvShows.value = tvShowResponse
                }
            }

            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    _tvShows.value = response.body()
                    FavoriteDatabaseHelper.createDb(context).tvShowDao().deleteResponse()
                    FavoriteDatabaseHelper.createDb(context).tvShowDao().addResponse(response.body()!!)
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                    var tvShowResponse = FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()
                    Log.e("Repository",tvShowResponse.toString())
                    tvShowResponse.results = ArrayList(FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows().results)
                    _tvShows.value = tvShowResponse
                }
            }

        })
    }

    fun getMovieGenres(networkListener: NetworkListener?){
        routes.getMovieGenre().enqueue(object : Callback<GenreResponse>{
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                _genreMovie.value = null
            }

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.genres.toString())
                    _genreMovie.value = response.body()
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                    _genreMovie.value = null
                }
            }

        })
    }

    fun getTvShowGenres(networkListener: NetworkListener?){
        routes.getTvShowGenre().enqueue(object : Callback<GenreResponse>{
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                _genreTvShow.value = null
            }

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.genres.toString())
                    _genreTvShow.value = response.body()
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                    _genreTvShow.value = null
                }
            }

        })
    }

    fun getFavoriteMovie(){
        val db = FavoriteDatabaseHelper.createDb(context).favMovieDao()
        val result = db.getFavMovie()
        _favMovie.value = result
    }

    fun getFavoriteTvShow(){
        val db = FavoriteDatabaseHelper.createDb(context).favTvShowDao()
        val result = db.getFavTvShow()
        _favTvShow.value = result
    }
}
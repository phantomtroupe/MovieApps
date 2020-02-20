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
    val _movies = MutableLiveData<ArrayList<MovieResponse>>()
    val _tvShows = MutableLiveData<ArrayList<TvShowResponse>>()
    val _genreMovie = MutableLiveData<GenreResponse>()
    val _genreTvShow = MutableLiveData<GenreResponse>()
    val _favMovie = MutableLiveData<Array<FavoriteMovieEntity>>()
    val _favTvShow = MutableLiveData<Array<FavoriteTvShowEntity>>()


    val movies = ArrayList<MovieResponse>()
    val tvShows = ArrayList<TvShowResponse>()


    fun getMovies(networkListener: NetworkListener?, page:Int = 1){
        routes.getMovies(page).enqueue(object: Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                movies.clear()
                var movieResponse = FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()
                if(movieResponse != null){
                    for(i in 0 until movieResponse.size){
                        movieResponse[i].results = ArrayList(FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()[i].results)
                        movies.add(movieResponse[i])
                    }
                    _movies.value = movies
                }
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movies.clear()
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    if(FavoriteDatabaseHelper.createDb(context).movieDao().checkIfMovieExist(response.body()?.page!!).isEmpty()){
                        FavoriteDatabaseHelper.createDb(context).movieDao().addResponse(response.body()!!)
                        Log.e("MovieRepository","Not Exist")
                    }
                    movies.addAll(FavoriteDatabaseHelper.createDb(context).movieDao().getMovies())
                    _movies.value = movies
                }else{
                    movies.clear()
                    networkListener?.onFailure(response.errorBody().toString())
                    var movieResponse = FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()
                    if(movieResponse != null){
                        for(i in 0 until movieResponse.size){
                            movieResponse[i].results = ArrayList(FavoriteDatabaseHelper.createDb(context).movieDao().getMovies()[i].results)
                            movies.add(movieResponse[i])
                        }
                        _movies.value = movies
                    }
                }
            }

        })
    }

    fun getTvShows(networkListener: NetworkListener?, page:Int){
        routes.getTvShow(page).enqueue(object: Callback<TvShowResponse>{
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
                tvShows.clear()
                var tvShowResponse = FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()
                if(tvShowResponse != null){
                    for(i in 0 until tvShowResponse.size){
                        tvShowResponse[i].results = ArrayList(FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()[i].results)
                        tvShows.add(tvShowResponse[i])
                    }
                    _tvShows.value = tvShows
                }
            }

            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                tvShows.clear()
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    if(FavoriteDatabaseHelper.createDb(context).tvShowDao().checkIfTvShowExist(response.body()?.page!!).isEmpty()){
                        FavoriteDatabaseHelper.createDb(context).tvShowDao().addResponse(response.body()!!)
                        Log.e("MovieRepository","Not Exist")
                    }
                    tvShows.addAll(FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows())
                    _tvShows.value = tvShows
                }else{
                    tvShows.clear()
                    networkListener?.onFailure(response.errorBody().toString())
                    var tvShowResponse = FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()
                    if(tvShowResponse != null){
                        for(i in 0 until tvShowResponse.size){
                            tvShowResponse[i].results = ArrayList(FavoriteDatabaseHelper.createDb(context).tvShowDao().getTvShows()[i].results)
                            tvShows.add(tvShowResponse[i])
                        }
                        _tvShows.value = tvShows
                    }
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
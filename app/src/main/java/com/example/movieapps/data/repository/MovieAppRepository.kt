package com.example.movieapps.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.movieapps.Network.NetworkListener
import com.example.movieapps.Network.Routes
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.tv_show.TvShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieAppRepository(val routes: Routes) {
    val _movies = MutableLiveData<MovieResponse>()
    val _tvShows = MutableLiveData<TvShowResponse>()


    fun getMovies(networkListener: NetworkListener?){
        routes.getMovies().enqueue(object: Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    _movies.value = response.body()
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                }
            }

        })
    }

    fun getTvShows(networkListener: NetworkListener?){
        routes.getTvShow().enqueue(object: Callback<TvShowResponse>{
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                networkListener?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                if(response.isSuccessful){
                    networkListener?.onSuccess(response.body()?.results.toString())
                    _tvShows.value = response.body()
                }else{
                    networkListener?.onFailure(response.errorBody().toString())
                }
            }

        })
    }
}
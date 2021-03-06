package com.example.movieapps.data.dao

import androidx.room.*
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies():List<MovieResponse>

    @Query("SELECT * FROM movies WHERE page = :page")
    fun checkIfMovieExist(page:Int):List<MovieResponse>

    @Insert
    fun addResponse(movieResponse: MovieResponse)

    @Insert
    fun addResult(result: Result)

    @Query("DELETE FROM movies")
    fun deleteResponse()
}
package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies():MovieResponse

    @Insert
    fun addResponse(movieResponse: MovieResponse)

    @Insert
    fun addResult(result: Result)

    @Query("DELETE FROM movies")
    fun deleteResponse()
}
package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapps.data.response.movie.Result

@Dao
interface MovieFavoriteDao {
    @Query("Select * From fav_movie")
    fun getFavMovie():Array<Result>

    @Insert
    fun addFavMovie(movie:Result)

    @Query("SELECT * FROM fav_movie where title = :title")
    fun getMovieByTitle(title:String):Array<Result>

    @Query("DELETE FROM fav_movie where title = :title")
    fun deleteMovieFav(title:String)
}
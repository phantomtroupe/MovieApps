package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.response.movie.Result

@Dao
interface MovieFavoriteDao {
    @Query("Select * From fav_movie")
    fun getFavMovie():Array<FavoriteMovieEntity>

    @Insert
    fun addFavMovie(movie:FavoriteMovieEntity)

    @Query("SELECT * FROM fav_movie where title = :title")
    fun getMovieByTitle(title:String):Array<FavoriteMovieEntity>

    @Query("DELETE FROM fav_movie where title = :title")
    fun deleteMovieFav(title:String)
}
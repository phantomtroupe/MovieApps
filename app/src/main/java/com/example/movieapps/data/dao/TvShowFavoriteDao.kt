package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapps.data.response.tv_show.Result

@Dao
interface TvShowFavoriteDao {

    @Query("Select * From fav_tvshow")
    fun getFavTvShow():Array<Result>

    @Insert
    fun addFavTvShow(tvShow:Result)

    @Query("SELECT * FROM fav_tvshow where name = :name")
    fun getTvShowByName(name:String):Array<Result>

    @Query("DELETE FROM fav_tvshow where name = :name")
    fun deleteTvShow(name:String)
}
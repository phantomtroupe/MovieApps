package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapps.data.entity.FavoriteTvShowEntity
import com.example.movieapps.data.response.tv_show.Result

@Dao
interface TvShowFavoriteDao {

    @Query("Select * From fav_tvshow")
    fun getFavTvShow():Array<FavoriteTvShowEntity>

    @Insert
    fun addFavTvShow(tvShow:FavoriteTvShowEntity)

    @Query("SELECT * FROM fav_tvshow where name = :name")
    fun getTvShowByName(name:String):Array<FavoriteTvShowEntity>

    @Query("DELETE FROM fav_tvshow where name = :name")
    fun deleteTvShow(name:String)
}
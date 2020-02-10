package com.example.movieapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapps.data.dao.MovieFavoriteDao
import com.example.movieapps.data.dao.TvShowFavoriteDao
import com.example.movieapps.data.response.movie.Result

@Database(entities = [Result::class,com.example.movieapps.data.response.tv_show.Result::class], version = 1)
@TypeConverters(Converter::class)
abstract class FavoriteDataBase : RoomDatabase() {
    abstract fun favMovieDao():MovieFavoriteDao
    abstract fun favTvShowDao():TvShowFavoriteDao
}
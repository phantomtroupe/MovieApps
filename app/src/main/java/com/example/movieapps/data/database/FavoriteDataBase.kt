package com.example.movieapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapps.data.dao.MovieDao
import com.example.movieapps.data.dao.MovieFavoriteDao
import com.example.movieapps.data.dao.TvShowDao
import com.example.movieapps.data.dao.TvShowFavoriteDao
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.entity.FavoriteTvShowEntity
import com.example.movieapps.data.response.movie.MovieResponse
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.data.response.tv_show.TvShowResponse

@Database(
    entities = [FavoriteMovieEntity::class,FavoriteTvShowEntity::class,Result::class,
        MovieResponse::class,TvShowResponse::class,
        com.example.movieapps.data.response.tv_show.Result::class], version = 1)
abstract class FavoriteDataBase : RoomDatabase() {
    @TypeConverters(Converter::class)
    abstract fun favMovieDao():MovieFavoriteDao
    @TypeConverters(Converter::class)
    abstract fun favTvShowDao():TvShowFavoriteDao
    @TypeConverters(MovieResultConverter::class)
    abstract fun movieDao():MovieDao
    abstract fun tvShowDao():TvShowDao
}
package com.example.movieapps.data.response.movie


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapps.data.database.MovieResultConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
@TypeConverters(MovieResultConverter::class)
data class MovieResponse(
    @PrimaryKey
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    @TypeConverters(MovieResultConverter::class)
    var results: ArrayList<Result>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
){
    constructor() : this(0,ArrayList<Result>(),0,0)
}
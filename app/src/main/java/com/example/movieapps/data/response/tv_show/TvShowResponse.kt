package com.example.movieapps.data.response.tv_show


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapps.data.database.Converter
import com.example.movieapps.data.database.TvShowResultConveter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshow")
@TypeConverters(TvShowResultConveter::class)
data class TvShowResponse(
    @PrimaryKey
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<Result>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
){
    constructor() : this(0,ArrayList<Result>(),0,0)
}
package com.example.movieapps.data.response.movie


import android.os.Parcelable
import androidx.room.*
import com.example.movieapps.data.database.Converter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "MovieResult")
@TypeConverters(Converter::class)
@Parcelize
data class Result(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    var genreIds: ArrayList<String>,
    @SerializedName("id")
    var id: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    @PrimaryKey
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: String,
    @SerializedName("vote_count")
    var voteCount: String
) : Parcelable
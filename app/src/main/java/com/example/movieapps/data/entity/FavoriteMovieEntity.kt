package com.example.movieapps.data.entity


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapps.data.database.Converter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "fav_movie")
@TypeConverters(Converter::class)
@Parcelize
data class FavoriteMovieEntity(
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
) : Parcelable{
    constructor() : this(false,"",ArrayList<String>(),"","","","",0.0,"","","",false,"","")
}
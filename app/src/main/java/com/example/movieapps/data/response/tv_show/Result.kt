package com.example.movieapps.data.response.tv_show


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "fav_tvshow")
@Parcelize
data class Result(
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("first_air_date")
    var firstAirDate: String,
    @SerializedName("genre_ids")
    var genreIds: ArrayList<String>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    @PrimaryKey
    var name: String,
    @SerializedName("origin_country")
    var originCountry: ArrayList<String>,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_name")
    var originalName: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
) : Parcelable{
    constructor() : this("","",ArrayList<String>(),0,"",ArrayList<String>(),"","","",0.0,"",0.0,0)
}
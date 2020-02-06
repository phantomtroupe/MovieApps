package com.example.movieapps.data.response.genres


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    var genres: List<Genre>
)
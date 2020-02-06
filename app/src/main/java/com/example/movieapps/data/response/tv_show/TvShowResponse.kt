package com.example.movieapps.data.response.tv_show


import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<Result>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)
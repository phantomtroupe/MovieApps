package com.example.movieapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.data.response.tv_show.TvShowResponse

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tvshow")
    fun getTvShows():List<TvShowResponse>

    @Query("SELECT * FROM tvshow WHERE page = :page")
    fun checkIfTvShowExist(page:Int):List<TvShowResponse>

    @Insert
    fun addResponse(tvShowResponse: TvShowResponse)

    @Insert
    fun addResult(result: Result)

    @Query("DELETE FROM tvshow")
    fun deleteResponse()
}
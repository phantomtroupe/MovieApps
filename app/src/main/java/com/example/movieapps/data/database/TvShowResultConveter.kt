package com.example.movieapps.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import com.example.movieapps.data.response.tv_show.Result

class TvShowResultConveter {
    @TypeConverter
    fun listToJSON(value: ArrayList<Result>) : String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun JSONToList(value: String) : ArrayList<Result> {
        val listType: Type = object : TypeToken<ArrayList<Result?>?>() {}.type
        val objects = Gson().fromJson(value,listType) as ArrayList<Result>
        return objects
    }
}
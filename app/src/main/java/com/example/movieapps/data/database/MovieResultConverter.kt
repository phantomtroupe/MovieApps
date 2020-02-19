package com.example.movieapps.data.database

import androidx.room.TypeConverter
import com.example.movieapps.data.response.movie.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MovieResultConverter {
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
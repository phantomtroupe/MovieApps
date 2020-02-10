package com.example.movieapps.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun listToJSON(value: ArrayList<String>) : String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun JSONToList(value: String) : ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        val objects = Gson().fromJson(value,listType) as ArrayList<String>
        return objects
    }
}
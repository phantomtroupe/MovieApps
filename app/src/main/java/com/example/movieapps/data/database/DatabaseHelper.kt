package com.example.movieapps.data.database

import android.content.Context
import androidx.room.Room

object DatabaseHelper {
    var db: FavoriteDataBase? = null

    fun createDb(context: Context): FavoriteDataBase {
        if(db != null){
            return db as FavoriteDataBase
        }else{
            db = Room.databaseBuilder(
                context,
                FavoriteDataBase::class.java,
                "FavoriteDataBase.db").allowMainThreadQueries().build()

            return db as FavoriteDataBase
        }
    }
}
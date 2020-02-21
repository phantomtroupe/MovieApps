package com.example.movieapps.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.movieapps.data.database.FavoriteDatabaseHelper

class DBReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        FavoriteDatabaseHelper.createDb(context!!).tvShowDao().deleteResponse()
        FavoriteDatabaseHelper.createDb(context!!).movieDao().deleteResponse()

        Log.e("Receiver","All Offline Data Removed!")
    }
}
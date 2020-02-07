package com.example.movieapps.Network

interface NetworkListener {
    fun onSuccess(message: String)
    fun onFailure(message:String)
}
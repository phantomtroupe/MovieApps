package com.example.movieapps.di

import android.content.Context
import com.example.movieapps.Network.Network
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.ui.ViewModelFactory

object Injection {
    fun provideRepository(context:Context):MovieAppRepository{
        return MovieAppRepository(Network.routes(),context)
    }

    fun provideViewModelFractory(context: Context):ViewModelFactory{
        return ViewModelFactory(provideRepository(context))
    }
}
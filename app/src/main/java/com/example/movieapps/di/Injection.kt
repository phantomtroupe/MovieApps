package com.example.movieapps.di

import com.example.movieapps.Network.Network
import com.example.movieapps.data.repository.MovieAppRepository
import com.example.movieapps.ui.ViewModelFactory

object Injection {
    fun provideRepository():MovieAppRepository{
        return MovieAppRepository(Network.routes())
    }

    fun provideViewModelFractory():ViewModelFactory{
        return ViewModelFactory(provideRepository())
    }
}
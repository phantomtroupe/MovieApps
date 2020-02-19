package com.example.movieapps.ui.tv_show


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.movieapps.R
import com.example.movieapps.data.entity.FavoriteTvShowEntity
import com.example.movieapps.data.response.tv_show.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.FavTvShowAdapter
import com.example.movieapps.ui.movie.FavoriteTvShowViewModel
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment : Fragment() {
    lateinit var favoriteTvShowViewModel: FavoriteTvShowViewModel
    lateinit var adapter: FavTvShowAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("OnCreate","Created")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Activity Created","Created")
        favoriteTvShowViewModel = ViewModelProvider(this, Injection.provideViewModelFractory(this.context!!)).get(FavoriteTvShowViewModel::class.java)
        favTvShowViewModel = favoriteTvShowViewModel
        initObserver()
        favoriteTvShowViewModel.setFavTvShow()
    }

    fun initObserver(){
        favoriteTvShowViewModel.getFavTvShow().observe(this, Observer<Array<FavoriteTvShowEntity>>{
                response ->
            Log.d("Favorite TvShow", response.toString())
            adapter = FavTvShowAdapter(this.context!!,response)
            rv_fav_movie.layoutManager = LinearLayoutManager(this.context)
            rv_fav_movie.adapter = adapter
        })
    }

    companion object{
        var favTvShowViewModel: FavoriteTvShowViewModel? = null
        fun updateData(){
            if(favTvShowViewModel != null){
                favTvShowViewModel?.setFavTvShow()
            }
        }
    }
}

package com.example.movieapps.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.movieapps.R
import com.example.movieapps.data.entity.FavoriteMovieEntity
import com.example.movieapps.data.response.movie.Result
import com.example.movieapps.di.Injection
import com.example.movieapps.ui.adapter.FavMovieAdapter
import com.example.movieapps.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment() {
    lateinit var favoriteMovieViewModel: FavoriteMovieViewModel
    lateinit var adapter:FavMovieAdapter
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
        favoriteMovieViewModel = ViewModelProvider(this,Injection.provideViewModelFractory(this.context!!)).get(FavoriteMovieViewModel::class.java)
        favMovieViewModel = favoriteMovieViewModel
        initObserver()
        favoriteMovieViewModel.setFavMovie()
    }

    fun initObserver(){
        favoriteMovieViewModel.getFavMovie().observe(this, Observer<Array<FavoriteMovieEntity>>{
            response ->
            Log.d("Favorite Movie", response.toString())
            adapter = FavMovieAdapter(this.context!!,response)
            rv_fav_movie.layoutManager = LinearLayoutManager(this.context)
            rv_fav_movie.adapter = adapter
        })
    }

    companion object{
        var favMovieViewModel: FavoriteMovieViewModel? = null
        fun updateData(){
            if(favMovieViewModel != null){
                favMovieViewModel?.setFavMovie()
            }
        }
    }
}

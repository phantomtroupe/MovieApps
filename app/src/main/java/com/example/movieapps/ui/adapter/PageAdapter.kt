package com.example.movieapps.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapps.ui.movie.FavoriteMovieFragment
import com.example.movieapps.ui.tv_show.FavoriteTvShowFragment

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return FavoriteMovieFragment()
        }else if(position == 1){
            return FavoriteTvShowFragment()
        }

        return Fragment()
    }

    
}
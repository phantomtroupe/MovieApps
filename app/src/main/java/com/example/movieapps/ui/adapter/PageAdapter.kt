package com.example.movieapps.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapps.ui.favorite.FavoriteFragment
import com.example.movieapps.ui.movie.FavoriteMovieFragment
import com.example.movieapps.ui.movie.MovieFragment
import com.example.movieapps.ui.tv_show.FavoriteTvShowFragment
import com.example.movieapps.ui.tv_show.TvShowFragment

class PageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        if(position == 0){
            return FavoriteMovieFragment()
        }else if(position == 1){
            return FavoriteTvShowFragment()
        }

        return Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0){
            return "Movie"
        }else if(position == 1){
            return "Tv Show"
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 2
    }


}
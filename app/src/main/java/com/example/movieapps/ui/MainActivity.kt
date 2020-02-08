package com.example.movieapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.movieapps.R
import com.example.movieapps.ui.favorite.FavoriteFragment
import com.example.movieapps.ui.movie.MovieFragment
import com.example.movieapps.ui.tv_show.TvShowFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.movie_fragment){
            loadFragment("movie")
        }else if(item.itemId == R.id.tvshow_fragment){
            loadFragment("tv")
        }else if(item.itemId == R.id.favorite_fragment){
            loadFragment("favorite")
        }
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loadFragment("movie")
        bottom_nav.setOnNavigationItemSelectedListener(this)
    }

    fun loadFragment(tag:String){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        var nextFragment = supportFragmentManager.findFragmentByTag(tag)

        if(currentFragment  != null){
            supportFragmentManager.beginTransaction().detach(currentFragment).commit()
        }

        if(nextFragment != null){
            supportFragmentManager.beginTransaction().attach(nextFragment).commit()
        }else{
            nextFragment = createFragment(tag)
            supportFragmentManager.beginTransaction().add(R.id.container,nextFragment,tag).commit()
        }
    }

    fun createFragment(tag:String):Fragment{
        var result = Fragment()
        if(tag == "movie"){
            result = MovieFragment()
        }else if(tag == "tv"){
            result = TvShowFragment()
        }else if(tag == "favorite"){
            result = FavoriteFragment()
        }

        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
}

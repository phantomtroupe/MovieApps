package com.example.movieapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.movieapps.R
import com.example.movieapps.ui.favorite.FavoriteFragment
import com.example.movieapps.ui.movie.MovieFragment
import com.example.movieapps.ui.setting.SettingActivity
import com.example.movieapps.ui.tv_show.TvShowFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val movieFragment = MovieFragment()
    val tvShowFragment = TvShowFragment()
    val favoriteFragment = FavoriteFragment()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.movie_fragment){
            supportActionBar?.elevation = 8f
            loadFragment("movie")
        }else if(item.itemId == R.id.tvshow_fragment){
            supportActionBar?.elevation = 8f
            loadFragment("tv")
        }else if(item.itemId == R.id.favorite_fragment){
            supportActionBar?.elevation = 0f
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
            val nextFragment = createFragment(tag)
            supportFragmentManager.beginTransaction().replace(R.id.container,nextFragment,tag).commit()
            Log.e("Fragment Name",nextFragment.tag)
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

        result.retainInstance = true

        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setting){
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}

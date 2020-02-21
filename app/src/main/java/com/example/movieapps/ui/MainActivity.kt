package com.example.movieapps.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.movieapps.R
import com.example.movieapps.data.preference.Preference
import com.example.movieapps.ui.favorite.FavoriteFragment
import com.example.movieapps.ui.movie.MovieFragment
import com.example.movieapps.ui.setting.SettingActivity
import com.example.movieapps.ui.tv_show.TvShowFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val REQ_CODE = 101

    val movieFragment = MovieFragment()
    val tvShowFragment = TvShowFragment()
    val favoriteFragment = FavoriteFragment()

    private var alarmManager: AlarmManager? = null

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

        if(alarmManager == null){
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this,DBReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this,REQ_CODE,intent,0)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY,8)
            }

            alarmManager?.setRepeating(
                AlarmManager.ELAPSED_REALTIME,SystemClock.elapsedRealtime(),60 * 5 * 60 * 1000,pendingIntent
            )

            Log.e("Alarm Manager","Created")
        }

        AlarmManager.INTERVAL_FIFTEEN_MINUTES

        Preference.addDefaultSetting(this)

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

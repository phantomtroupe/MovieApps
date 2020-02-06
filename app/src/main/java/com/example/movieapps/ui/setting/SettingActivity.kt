package com.example.movieapps.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapps.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar)
        supportActionBar?.title =  resources.getString(R.string.settings)
    }
}

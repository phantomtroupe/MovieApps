package com.example.movieapps.data.preference

import android.content.Context
import android.content.SharedPreferences

class Preference {
    companion object {
        val PREF_NAME = "APP_PREF"
        val KEY_SETTING_CONSTANT = "SETTING"

        fun getSharedPreference(context: Context) :  SharedPreferences{
            return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        }

        fun getSettingValue(context: Context) : Boolean{
            return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).getBoolean(
                KEY_SETTING_CONSTANT,false)
        }

        fun addDefaultSetting(context: Context){
            if(!context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).contains(KEY_SETTING_CONSTANT)){
                var editor = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).edit()
                editor.putBoolean(KEY_SETTING_CONSTANT,true)
                editor.apply()
            }
        }

        fun editSetting(context: Context, active:Boolean){
            var editor = context.getSharedPreferences(KEY_SETTING_CONSTANT,Context.MODE_PRIVATE).edit()
            editor.putBoolean(KEY_SETTING_CONSTANT,active)
        }
    }
}
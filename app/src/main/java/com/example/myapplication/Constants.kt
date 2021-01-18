package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.activities.AppWindowActivity
import org.intellij.lang.annotations.Language
import java.util.*

class Constants{
    companion object{
        const val TRAINING_FILE = "Training.json"
        const val CALENDAR_FILE = "Calendar.json"
        const val CONFIG_FILE = "Config.json"
        const val PARTS_PER_LIST = 3

        const val MAX_REPS_PERCENTAGE = 2

        const val PREFERENCE = "preference.pre";
        //Language
        const val LANG:String = "lang"
        const val LANG_EN:String = "en"
        const val LANG_PL:String = "pl"
        var LANG_CURRENT = "en"
        var LANG_OLD = "en"
        //Theme
        const val THEME = "theme"
        var THEME_CURRENT = -1
        const val THEME_LIGHT = R.style.Theme_Material_Light
        const val THEME_DARK = R.style.Theme_Material_Dark
    }
}
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
        const val CONFIG_FILE = "Config.json"
        const val PARTS_PER_LIST = 3
        //Language
        const val PREFERENCE = "preference.pre";
        const val LANG:String = "lang"
        const val LANG_EN:String = "en"
        const val LANG_PL:String = "pl"
        var LANG_CURRENT = "en"
        var LANG_OLD = "en"



    }
}
package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import com.example.myapplication.Constants
import com.example.myapplication.R

@Suppress("DEPRECATION")
class MainActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val planer = findViewById<CardView>(R.id.cardViewPlanner)

        planer.setOnClickListener{
                val intent =  Intent(applicationContext, PlannerActivity::class.java)
                startActivity(intent)
        }

        val statistics = findViewById<CardView>(R.id.cardViewStatistics)
        statistics.setOnClickListener{
//            val intent =  Intent(applicationContext, StatisticsActivity::class.java)
//            startActivity(intent)
            if(Constants.LANG_CURRENT == Constants.LANG_PL)
                setLangPref(Constants.LANG_EN)
            else
                setLangPref(Constants.LANG_PL)
        }
        val training = findViewById<CardView>(R.id.cardViewTraining)
        training.setOnClickListener{
            val intent =  Intent(applicationContext, TrainingActivity::class.java)
            startActivity(intent)
        }
        val calendar = findViewById<CardView>(R.id.cardViewCalendar)
        calendar.setOnClickListener{
            val intent =  Intent(applicationContext, CalendarActivity::class.java)
            startActivity(intent)
        }

        getLangPref()
        if(Constants.LANG_CURRENT != Constants.LANG_OLD)
            restartActivity()
    }
    private fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }
    private fun setLangPref(localeCode : String){
        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        with (pref.edit()) {
            putString(Constants.LANG, localeCode)
            apply()
        }
        Constants.LANG_OLD = Constants.LANG_CURRENT
        Constants.LANG_CURRENT = localeCode
        restartActivity()
    }
    private fun getLangPref() {
        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        Constants.LANG_OLD = Constants.LANG_CURRENT
        Constants.LANG_CURRENT = pref.getString(Constants.LANG, Constants.LANG_EN).toString()
    }
}
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

/*TODO

*/


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val planer = findViewById<CardView>(R.id.cardViewPlanner)
        planer.setOnClickListener{
                val intent =  Intent(applicationContext,PlannerActivity::class.java)
                startActivity(intent)
        }
        val statistics = findViewById<CardView>(R.id.cardViewStatistics)
        statistics.setOnClickListener{
            val intent =  Intent(applicationContext,StatisticsActivity::class.java)
            startActivity(intent)
        }
        val training = findViewById<CardView>(R.id.cardViewTraining)
        training.setOnClickListener{
            val intent =  Intent(applicationContext,TrainingActivity::class.java)
            startActivity(intent)
        }
        val calendar = findViewById<CardView>(R.id.cardViewCalendar)
        calendar.setOnClickListener{
            val intent =  Intent(applicationContext,CalendarActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

}
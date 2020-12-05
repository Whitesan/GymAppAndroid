package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

import com.example.myapplication.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
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
            val intent =  Intent(applicationContext, StatisticsActivity::class.java)
            startActivity(intent)
        }
        val training = findViewById<CardView>(R.id.cardViewTraining)
        training.setOnClickListener{
            val intent =  Intent(applicationContext, TrainingActivity::class.java)
            startActivity(intent)
        }
        val calendar = findViewById<CardView>(R.id.cardViewCalendar)
        calendar.setOnClickListener{
            val intent =  Intent(applicationContext, EnterExerciseNameActivity::class.java)
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
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

}
package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.example.myapplication.R

@Suppress("DEPRECATION")
class StatisticsActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val button = findViewById<ImageView>(R.id.backStatistics)
        button.setBackListener(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
    }
}
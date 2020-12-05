package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.example.myapplication.R

@Suppress("DEPRECATION")
class CalendarActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val button = findViewById<ImageView>(R.id.backCalendar)
        button.setOnClickListener{
            val intent =  Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
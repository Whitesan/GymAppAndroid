package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.myapplication.R

@Suppress("DEPRECATION")
class PlannerActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)
        val back = findViewById<ImageView>(R.id.backPlanner)
        back.setOnClickListener{
                val intent =  Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
        }
        val createTraining = findViewById<CardView>(R.id.createTrainingCardView)
        createTraining.setOnClickListener{
            val intent =  Intent(applicationContext, CreateTrainingActivity::class.java)
            startActivity(intent)
        }



    }
}
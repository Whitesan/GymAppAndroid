package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R

@Suppress("DEPRECATION")
class TrainingsListActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainings_list)
        val button = findViewById<ImageView>(R.id.backTraningsList)
        button.setOnClickListener {
            val intent = Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }
    }
}
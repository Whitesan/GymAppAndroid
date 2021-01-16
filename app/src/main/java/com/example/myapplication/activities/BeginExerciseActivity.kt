package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.myapplication.R

class BeginExerciseActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin_exercise)

        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener { onBackPressed() }


        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.setOnClickListener {
            startActivity(Intent(applicationContext, TrainingActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

        }
    }


    override fun onBackPressed() {
        startActivity(Intent(applicationContext, TrainingActivity::class.java))
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

    }
}
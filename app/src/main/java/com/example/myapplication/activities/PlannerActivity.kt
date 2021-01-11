package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise

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
            CreateTrainingActivity.exerciseList = ArrayList<Exercise>()
            CreateTrainingActivity.enteredText = ""
            CreateTrainingActivity.editedIndex = -1
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

        }

        val createTrainingsListActivity = findViewById<CardView>(R.id.createTrainingsListCardViev)
        createTrainingsListActivity.setOnClickListener{
            val intent =  Intent(applicationContext, TrainingsListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
        }
    }
}
package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.Stopwatch
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Series
import com.example.myapplication.exercises.Training

class BeginExerciseActivity : AppWindowActivity() {
    private lateinit var actualExercise: Exercise
    private lateinit var actualSet: Series
    private lateinit var todayTraining: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin_exercise)
        startClock(null)

        getTraining()
        setRepsAndWeight()
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener { onBackPressed() }


        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.setOnClickListener {
            onBackPressed()

        }
    }
    private fun getTraining(){
        actualExercise = super.getIntent().getSerializableExtra("Exercise") as Exercise
        actualSet = super.getIntent().getSerializableExtra("Series")as Series
        todayTraining = super.getIntent().getSerializableExtra("Training")as Training
    }
    private fun setRepsAndWeight(){
        val name:TextView = findViewById(R.id.exerciseName)
        name.text = actualExercise.getName()

        val reps:TextView= findViewById(R.id.reps)
        reps.text = actualSet.reps.toString()

        val weight:TextView= findViewById(R.id.weight)
        weight.text = actualSet.weight.toString()
        if(actualExercise.getPart()!!.isCardio()){
            val weightDesc:TextView = findViewById(R.id.weightDesc)
            weightDesc.text = getString(R.string.meters)
        }


    }
    override fun onBackPressed() {
        intent = Intent(applicationContext, TrainingActivity::class.java)
        intent.putExtra("EditTraining",todayTraining)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
    }
    private fun startClock(descStartTime: Int?) {
        val clock: TextView = findViewById(R.id.clock)
        Stopwatch(0,null)
            .start(100,clock)
    }
}
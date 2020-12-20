package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Training
import com.example.myapplication.recycler_view.ExerciseListAdapter
import com.example.myapplication.recycler_view.TrainingListAdapter

@Suppress("DEPRECATION")
class ExerciseListActivity : AppWindowActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_training_exercises_list)
        val button = findViewById<ImageView>(R.id.backTrainingsList)
        button.setOnClickListener {
            val intent = Intent(applicationContext, TrainingsListActivity::class.java)
            startActivity(intent)
        }

        val training = TrainingListAdapter.currentTraining

        createExercisesList(training)
        Log.i("ExerciseListActivity", training.showExercise())
    }

    private fun createExercisesList(training : Training){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_training_exercise_list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ExerciseListAdapter(training.exerciseList)
    }
}
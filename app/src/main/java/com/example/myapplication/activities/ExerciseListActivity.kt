package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Training
import com.example.myapplication.recycler_view.ExerciseListAdapter
import com.example.myapplication.recycler_view.TrainingListAdapter

@Suppress("DEPRECATION")
class ExerciseListActivity : AppWindowActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_training_exercises_list)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setBackListener(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

        val training = TrainingListAdapter.currentTraining
        createExercisesList(training)
    }

    private fun createExercisesList(training : Training){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_training_exercise_list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ExerciseListAdapter(training.getExercises(), this)

        val title = findViewById<TextView>(R.id.navBarTitle)
        title.text = training.getName()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
    }
}
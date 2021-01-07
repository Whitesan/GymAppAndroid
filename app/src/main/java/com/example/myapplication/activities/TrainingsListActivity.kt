package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.TrainingListAdapter

@Suppress("DEPRECATION")
class TrainingsListActivity : AppWindowActivity() {
    companion object {
        var trainingsGuiList = ArrayList<Training>()
        var trainingsList: Trainings = Trainings(ArrayList())
        var editedIndex = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_trainings_list)
        val button = findViewById<ImageView>(R.id.backTrainingsList)
        button.setOnClickListener {
            val intent = Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }

        //Loading list from JSON file
        //List is using by recycle_viewer
        trainingsList = TrainingJsonConverter.loadTrainingJson("$filesDir/Training.json")
        trainingsGuiList = trainingsList.trainingList

        createVisualTrainingsList()
    }

    private fun createVisualTrainingsList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_training_list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = TrainingListAdapter(trainingsGuiList, "$filesDir/Training.json", this)
    }
}
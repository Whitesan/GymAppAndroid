package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.SelectAnotherTrainingAdapter

class SelectAnotherTrainingActivity : AppWindowActivity() {
    companion object {
        var trainingsGuiList = ArrayList<Training>()
        var trainingsList: Trainings = Trainings(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_select_another_training)
        val button = findViewById<ImageView>(R.id.backTrainingsList)
        button.setOnClickListener {
            val intent = Intent(applicationContext, TrainingActivity::class.java)
            startActivity(intent)
        }

        trainingsList = TrainingJsonConverter.loadTrainingJson("$filesDir/Training.json")
        trainingsGuiList = trainingsList.trainingList

        createVisualTrainingsList()
    }

    private fun createVisualTrainingsList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_training_list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = SelectAnotherTrainingAdapter(trainingsGuiList)
    }
}
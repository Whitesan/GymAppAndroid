package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Trainings

@Suppress("DEPRECATION")
class TrainingsListActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainings_list)
        val button = findViewById<ImageView>(R.id.backTrainingsList)
        button.setOnClickListener {
            val intent = Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }
        val json : TrainingJsonConverter = TrainingJsonConverter()
        val yourFilePath = filesDir.toString() + "/" + "Training.json"
        var trainings : Trainings? = json.fromJson(yourFilePath)
        if(trainings == null)
        {
            trainings = Trainings(ArrayList())
            json.toJson(trainings,yourFilePath)
        }
        Toast.makeText(this, trainings.showTrainings() +" " , Toast.LENGTH_LONG).show()
        val trainingList = findViewById<LinearLayout>(R.id.trainingList)
        for(t in trainings.trainingList)
        {
           val textWiew = TextView(this)
            var name :String = ""
            name +=t.getName() + " : "
            for(e in t.exerciseList)
            {
                name += e.getName()+","
            }
            textWiew.text = name

            trainingList.addView(textWiew)
        }
    }
}
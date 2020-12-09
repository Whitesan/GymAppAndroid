package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter

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
        val training= json.fromJson(yourFilePath)
        Toast.makeText(this, training.getName() +" ", Toast.LENGTH_LONG).show()
    }
}
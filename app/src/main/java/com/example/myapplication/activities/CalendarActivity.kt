package com.example.myapplication.activities

import CalendarAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CalendarJsonConverter
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.exercises.Calendar
import com.example.myapplication.Constants.Companion.CALENDAR_FILE
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.CurrentDay
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.TrainingListAdapter

@Suppress("DEPRECATION")
class CalendarActivity : AppWindowActivity() {
    private var days = ArrayList<String>();

    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener{onBackPressed()}

        days.addAll(listOf(
            getString(R.string.monday),
            getString(R.string.tuesday),
            getString(R.string.wednesday),
            getString(R.string.thursday),
            getString(R.string.friday),
            getString(R.string.saturday),
            getString(R.string.sunday)
        ))

        createRecycleView()
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
    }

    private fun createRecycleView() {
        val json: TrainingJsonConverter = TrainingJsonConverter()
        val yourFilePath = "$filesDir/${Constants.TRAINING_FILE}"
        var trainings : Trainings? = json.fromJson(yourFilePath)


        val recyclerView = findViewById<RecyclerView>(R.id.rv_days)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = CalendarAdapter(days, this, trainings!!.trainingList)
    }

}
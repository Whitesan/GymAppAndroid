package com.example.myapplication.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.*
import com.example.myapplication.CalendarJsonConverter
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.exercises.Calendar
import com.example.myapplication.Constants.Companion.CALENDAR_FILE
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.CurrentDay
import com.example.myapplication.exercises.Trainings

@Suppress("DEPRECATION")
class CalendarActivity : AppWindowActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setBackListener(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
        val listOfDays = findViewById<LinearLayout>(R.id.listOfDays)
        val json: TrainingJsonConverter = TrainingJsonConverter()
        val yourFilePath = "$filesDir/${Constants.TRAINING_FILE}"

        var trainings : Trainings? = json.fromJson(yourFilePath)

        loadCalendar(trainings!!,listOfDays)
        applyClickListener(trainings,listOfDays,json)

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
    }


    fun applyClickListener(trainings: Trainings, listOfDays : LinearLayout,json: TrainingJsonConverter)
    {
        val yourFilePath = "$filesDir/${Constants.TRAINING_FILE}"
        for (i in 0 until listOfDays.getChildCount()) {
            val v: LinearLayout = listOfDays.getChildAt(i) as LinearLayout

            for(j in 0 until v.childCount)
            {
                val name: TextView = v.getChildAt(1) as TextView
                val button: Button = v.getChildAt(2) as Button
                if(j == 1)
                {
                    //Add training onClick
                    name.setOnClickListener {
                        if (name.text == "Add training") {
                            val currentDay :CurrentDay = CurrentDay()
                            currentDay.day = i
                            val intent = Intent(applicationContext, CalendarTrainingListActivity::class.java)
                            intent.putExtra("extra_object", currentDay)
                            startActivity(intent)



                            //json.toJson(calendar, yourFilePath)
                        }
                    }
                }
                if(j == 2)
                {
                    //Delete onCLick
                    button.setOnClickListener {
                        if (button.text == "DELETE") {
                            button.text = ""
                            trainings.getTrainingByDay(i)?.getDays()?.remove(i);
                            name.text = "Add training"
                            json.toJson(trainings, yourFilePath)
                        }
                    }
                }
            }
        }
    }
    fun loadCalendar(trainings : Trainings,listOfDays : LinearLayout)
    {
        for (i in 0 until listOfDays.getChildCount()) {
            val v: LinearLayout = listOfDays.getChildAt(i) as LinearLayout

            for(j in 0 until v.childCount)
            {
                if(j == 1)
                {
                    val c: TextView = v.getChildAt(j) as TextView
                    if(trainings == null)
                    {
                        c.text = "Add training"
                    }
                    else
                    {
                        var training = trainings.getTrainingByDay(i)
                        if(training== null)
                            c.text = "Add training"
                        else
                        {
                            c.text = training.getName()
                            val button: Button = v.getChildAt(j+1) as Button
                            button.text = "DELETE"
                        }
                    }


                }
            }
        }
    }
}
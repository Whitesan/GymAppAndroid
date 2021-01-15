package com.example.myapplication.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.*
import com.example.myapplication.CalendarJsonConverter
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.exercises.Calendar
import com.example.myapplication.Constants.Companion.CALENDAR_FILE
import com.example.myapplication.TrainingJsonConverter

@Suppress("DEPRECATION")
class CalendarActivity : AppWindowActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setBackListener(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
        val listOfDays = findViewById<LinearLayout>(R.id.listOfDays)
        val json: CalendarJsonConverter = CalendarJsonConverter()
        val yourFilePath = "$filesDir/${Constants.CALENDAR_FILE}"


        /*
        var calendar : Calendar = Calendar()
        calendar.init()
        json.toJson(calendar,yourFilePath)
*/


        var calendar : Calendar? = json.fromJson(yourFilePath)
        if(calendar == null)
        {
            calendar = Calendar()
            calendar.init()
            json.toJson(calendar,yourFilePath)
        }
        loadCalendar(calendar,listOfDays)
        applyClickListener(calendar,listOfDays,json)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
    }

    //fill all training
    fun initDays(listOfDays : LinearLayout)
    {
        for (i in 0 until listOfDays.getChildCount()) {
            val v: LinearLayout = listOfDays.getChildAt(i) as LinearLayout

            for(j in 0 until v.childCount)
            {
                if(j == 1)
                {
                    val c: TextView = v.getChildAt(j) as TextView
                    c.text = "Add training"
                    c.setTextColor(Color.parseColor("#510aad3f"))
                    c.textSize = 30.0f;

                }
            }
        }
    }
    fun applyClickListener(calendar : Calendar , listOfDays : LinearLayout,json: CalendarJsonConverter)
    {
        val yourFilePath = "$filesDir/${Constants.CALENDAR_FILE}"
        for (i in 0 until listOfDays.getChildCount()) {
            val v: LinearLayout = listOfDays.getChildAt(i) as LinearLayout

            for(j in 0 until v.childCount)
            {
                val name: TextView = v.getChildAt(1) as TextView
                val button: Button = v.getChildAt(2) as Button
                if(j == 1)
                {
                    name.setOnClickListener {
                        if (calendar.dayList[i].trainingName == "empty") {
                            name.text = "Clicked"
                            button.text = "DELETE"
                            calendar.dayList[i].trainingName = "clicked"
                            json.toJson(calendar, yourFilePath)
                        }
                    }
                }
                if(j == 2)
                {
                    button.setOnClickListener {
                        if (button.text == "DELETE") {
                            calendar.dayList[i].trainingName = "empty"
                            button.text = ""
                            name.text = "Add training"
                            json.toJson(calendar, yourFilePath)
                        }
                    }
                }
            }
        }
    }
    fun loadCalendar(calendar : Calendar,listOfDays : LinearLayout)
    {
        for (i in 0 until listOfDays.getChildCount()) {
            val v: LinearLayout = listOfDays.getChildAt(i) as LinearLayout

            for(j in 0 until v.childCount)
            {
                if(j == 1)
                {
                    val c: TextView = v.getChildAt(j) as TextView
                    var trainingName = calendar.dayList[i].trainingName
                    if(trainingName == "empty")
                        c.text = "Add training"
                    else
                    {
                        c.text = trainingName
                        val button: Button = v.getChildAt(j+1) as Button
                        button.text = "DELETE"
                    }

                }
            }
        }
    }
}
package com.example.myapplication.exercises

import android.util.Log
import java.io.Serializable
import java.util.Calendar

class Trainings(var trainingList: ArrayList<Training>) : Serializable {

    fun showTrainings(): String {
        var list: String = ""
        for (t in trainingList) {
            list += t.getName() + "; "
        }
        return list
    }

    fun getTrainingByDay(day: Int): Training? {
        for (training in trainingList) {
            Log.println(
                Log.INFO,
                null,
                "TEST2  " + training.getDays()+" \n"
            )
            if (training.hasDay(day))
                return training
        }

        return null
    }
}
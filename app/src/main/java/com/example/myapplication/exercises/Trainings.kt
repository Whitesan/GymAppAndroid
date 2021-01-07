package com.example.myapplication.exercises

import java.io.Serializable
import java.time.DayOfWeek

class Trainings (var trainingList :ArrayList<Training>):Serializable{
    fun showTrainings():String
    {
        var list : String =""
        for (t in trainingList)
        {
            list += t.getName()
        }
        return list;
    }
    fun getTrainingByDay(day:Int):Training?{
        for(training in trainingList){
            if(training.getDay()==day)
                return training
        }
        return null
    }

}